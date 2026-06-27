package com.seek.food.gateway.Filter;


import com.seek.food.dto.Common.ErrorCodeEnum;
import com.seek.food.gateway.Config.CommonRedisKeyConfig;
import com.seek.food.gateway.Config.JWTConfig;
import com.seek.food.gateway.Config.RequestPathConfig;
import com.seek.food.util.JWT.JWTHeaderSign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.seek.food.util.JWT.JWTUtil;

import java.util.List;


@Order(1)       //过滤器的顺序，越小就越先执行
@Component      //使其被扫描到
@RefreshScope
public class TokenFilter implements GlobalFilter {

    private final JWTConfig jwtConfig;
    private final RequestPathConfig requestPathConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final JWTHeaderSign[] jwtHeaderSigns;
    private final CommonRedisKeyConfig commonRedisKeyConfig;
    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    // 构造器注入
    @Autowired
    public TokenFilter(JWTConfig jwtConfig,RequestPathConfig requestPathConfig,StringRedisTemplate stringRedisTemplate,
                       CommonRedisKeyConfig commonRedisKeyConfig) {
        this.jwtConfig = jwtConfig;
        this.requestPathConfig = requestPathConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jwtHeaderSigns=JWTHeaderSign.getHeaderSignArr(jwtConfig.getSecretKey(),jwtConfig.getHeaderSign(), jwtConfig.getHeaderSeparator(), jwtConfig.getTokenName());
        this.commonRedisKeyConfig = commonRedisKeyConfig;
    }

    //token处理拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        //实现方法，其中exchange用于获取和设置请求头、响应头。chain用于放行
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        logger.info("请求路径:{} ,进入TokenFilter",path);
        //检查该请求路径是否需要直接放行
        if(requestPathConfig.checkAllowPath(path))return chain.filter(exchange);
        //检查该请求路径是否为封禁路径
        if (requestPathConfig.checkRejectPath(path))return reject(exchange);
        //尝试获取Id
        long ownId=checkToken(getToken(request));
        //token是否有效，有效则放行
        if (ownId!=JWTUtil.FailResult)return chain.filter(getNewExchange(request,exchange,ownId));
        //拒绝放行
        logger.warn("token验证失败");
        return reject(exchange);
        }

        // 通过安全的HttpOnly Cookie来获取token
    private String getToken(ServerHttpRequest request){
        //获取token
        // 1. 获取全部Cookie集合
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        // 2. 根据Cookie名称拿token（登录接口Set-Cookie里的key，比如access_token）
        List<HttpCookie> tokenCookies = cookies.get(jwtConfig.getRequestTokenName());
        String token = null;
        if (tokenCookies != null && !tokenCookies.isEmpty())token = tokenCookies.getFirst().getValue();
        return token;
    }

        //检验token是否有效
    private long checkToken(String token){
        long result = JWTUtil.FailResult;
        //检测token是否为空
        if(token == null||token.isEmpty())return result;
        //检查token是否有效
        result= JWTUtil.jwtCheckByList(token,jwtConfig.getHeaderSeparator(), jwtHeaderSigns);
        //检验redis是否存在该token
        if(stringRedisTemplate.opsForHash().get(commonRedisKeyConfig.getLoginToken()+result, token)==null) return JWTUtil.FailResult;
        return result;
    }

        //构建新的上下文
    private ServerWebExchange getNewExchange(ServerHttpRequest request, ServerWebExchange exchange,long ownId){
        // 构造新请求，追加解析后的用户信息到请求头
        ServerHttpRequest newReq = request.mutate()
                .headers(headers -> {
                    // 清空客户端伪造的同名header
                    headers.remove(jwtConfig.getHeaderTokenName());
                    // 新增，此时列表只有一条
                    headers.add(jwtConfig.getHeaderTokenName(), String.valueOf(ownId));
                })
                .build();
        //放入id
        return exchange.mutate().request(newReq).build();
    }

        //拒绝放行
    private Mono<Void> reject(ServerWebExchange exchange){
        logger.warn("非法请求被FilterFilter拦截");
        exchange.getResponse().setRawStatusCode(ErrorCodeEnum.UNAUTHORIZED.getCode());      //设置状态码
        return exchange.getResponse().setComplete();        //拒绝请求
    }


}














