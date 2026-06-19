package com.seek.food.user.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.user.Enum.JWTEnum;
import com.seek.food.util.CommonUtil.JWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private JWTEnum jwtEnum;
    @PostMapping
    public Result login(long userId, HttpServletResponse response) {
        // 1. 登录校验成功，生成JWT accessToken
        String accessToken = JWT.obtainJwtByLong(userId,jwtEnum.getUserSerectKey());

        // 2. 构建安全Cookie
        // 构建Servlet Cookie
        Cookie cookie = new Cookie("access_token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
        return Result.success("good",null);
    }











}
