package com.seek.food.util.JWT;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

public class TokenUtil {
    //统一获取token
    public static void getToken(Long id, HttpServletResponse response, String secretKey, long expireMillSecondsTime
    , String requestTokenName, String headerSign, String headerSeparator){
        //登录校验成功，生成JWT Token
        String accessToken = JWTUtil.obtainJwtByLong(id,secretKey,expireMillSecondsTime);
        // 构建Servlet Cookie
        Cookie cookie = new Cookie(requestTokenName, headerSign+headerSeparator+accessToken);
        cookie.setHttpOnly(true);
        //生产环境改为true
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int) (expireMillSecondsTime/1000));
        response.addCookie(cookie);
    }
}
