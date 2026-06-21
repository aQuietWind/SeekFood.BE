package com.seek.food.gateway.Filter;


import com.seek.food.gateway.Enum.JWTConfig;
import com.seek.food.gateway.Enum.RedisKeyConfig;
import com.seek.food.gateway.Enum.RequestPathConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.seek.food.util.CommonUtil.JWT;

import java.util.List;


@Order(1)       //过滤器的顺序，越小就越先执行
@Component      //使其被扫描到
@RefreshScope
public class TokenFilter implements GlobalFilter, ApplicationContextAware {

    private JWTConfig jwtConfig;
    private RequestPathConfig requestPathConfig;
    private StringRedisTemplate stringRedisTemplate;
    private RedisKeyConfig redisKeyConfig;
    // 容器初始化好后，手动获取Bean
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
        this.jwtConfig = applicationContext.getBean(JWTConfig.class);
        this.requestPathConfig = applicationContext.getBean(RequestPathConfig.class);
        this.redisKeyConfig = applicationContext.getBean(RedisKeyConfig.class);
    }

    //token处理拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        //实现方法，其中exchange用于获取和设置请求头、响应头。chain用于放行
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        System.out.println(path);

        //检查该请求路径是否需要直接放行
        if(requestPathConfig.checkAllowPath(path))return chain.filter(exchange);
        //检查该请求路径是否为封禁路径
        if (requestPathConfig.checkRejectPath(path))return reject(exchange);
        //尝试获取Id
        long ownId=checkToken(getToken(request));
        //token是否有效，有效则放行
        if (ownId!=0) return chain.filter(getNewExchange(request,exchange,ownId));
        //拒绝放行
        return reject(exchange);
        }


        // 通过安全的HttpOnly Cookie来获取token
        private String getToken(ServerHttpRequest request){
            //获取token
            // 1. 获取全部Cookie集合
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            // 2. 根据Cookie名称拿token（登录接口Set-Cookie里的key，比如access_token）
            List<HttpCookie> tokenCookies = cookies.get("access_token");

            String token = null;
            if (tokenCookies != null && !tokenCookies.isEmpty())token = tokenCookies.get(0).getValue();
            return token;
        }


        //检验token是否有效
        private long checkToken(String token){
            long result = 0;
            //检测token是否为空
            if(token == null||token.isEmpty())return result;
            //检查token是否有效
            try {
                //判断是否为user
                result=JWT.jwtCheckToLong(token, jwtConfig.getUserSerectKey());
            } catch (Exception e) {
                try {
                    //再判断是否为merchant
                    result = JWT.jwtCheckToLong(token, jwtConfig.getUserSerectKey());
                } catch (Exception ex) {
                    return result;
                }
            }
            if(stringRedisTemplate.opsForHash().get(redisKeyConfig.getLoginTokenKey()+result, token)==null)return 0;
            return result;
        }

        //构建新的上下文
        private ServerWebExchange getNewExchange(ServerHttpRequest request, ServerWebExchange exchange,long ownId){
            // 构造新请求，追加解析后的用户信息到请求头
            ServerHttpRequest newReq = request.mutate()
                    .headers(headers -> {
                        // 清空客户端伪造的同名header
                        headers.remove("X-Token-Id");
                        // 新增，此时列表只有一条
                        headers.add("X-Token-Id", String.valueOf(ownId));
                    })
                    .build();
            //放入id
            return exchange.mutate().request(newReq).build();
        }

        //拒绝放行
        private Mono<Void> reject(ServerWebExchange exchange){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);      //设置状态码
            return exchange.getResponse().setComplete();        //拒绝请求
        }


}














