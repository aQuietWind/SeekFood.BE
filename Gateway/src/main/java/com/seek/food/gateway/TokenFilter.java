package com.seek.food.gateway;


import com.seek.food.gateway.Enum.JWTEnum;
import com.seek.food.gateway.Enum.RequestPathEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.seek.food.util.CommonUtil.JWT;

import java.util.List;


@Order(2)       //过滤器的顺序，越小就越先执行
@Component      //使其被扫描到
public class TokenFilter implements GlobalFilter {      //实现接口
    @Autowired
    private JWTEnum jwtEnum;
    @Autowired
    private RequestPathEnum requestPathEnum;

    //token模拟处理拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        //实现方法，其中exchange用于获取和设置请求头、响应头。chain用于放行
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        System.out.println(path);
        //检查该路径是否需要直接放行
        if(requestPathEnum.checkAllowPath(path)){
            return chain.filter(exchange);      //放行，或者自动交由下一个全局过滤器
        }
        //检查该路径是否为封禁路径
        if (requestPathEnum.checkRejectPath(path)){
            //拒绝放行
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);      //设置状态码
            return exchange.getResponse().setComplete();        //拒绝请求
        }
        String token=getToken(request);
        if (checkToken(token)){
            return chain.filter(exchange);
        }
        //拒绝放行
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);      //设置状态码
        return exchange.getResponse().setComplete();        //拒绝请求
        }

        // 通过安全的HttpOnly Cookie来获取token
        private String getToken(ServerHttpRequest request){
            //获取token
            // 1. 获取全部Cookie集合
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            // 2. 根据Cookie名称拿token（登录接口Set-Cookie里的key，比如access_token）
            List<HttpCookie> tokenCookies = cookies.get("access_token");

            String token = null;
            if (tokenCookies != null && !tokenCookies.isEmpty()) {
                token = tokenCookies.get(0).getValue();
            }
            return token;
        }

        //检验token是否有效
        private boolean checkToken(String token){
            //检测token是否为空
            if(token == null||token.isEmpty()){
                return false;
            }
            //检查token是否有效
            try {
                //判断是否为user
                long result=JWT.jwtCheckToLong(token,jwtEnum.getUserSerectKey());
                return true;
            } catch (Exception e) {
                try {
                    //再判断是否为merchant
                    long result = JWT.jwtCheckToLong(token, jwtEnum.getUserSerectKey());
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        }

}














