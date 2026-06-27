package com.seek.food.user.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.user.Config.JWTConfig;
import com.seek.food.util.JWT.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private JWTConfig jwtConfig;
    @PostMapping
    public Result login(long userId, HttpServletResponse response) {
        // 1. 登录校验成功，生成JWT accessToken
        String accessToken = JWTUtil.obtainJwtByLong(userId,jwtConfig.getUserSerectKey());

        // 2. 构建安全Cookie
        // 构建Servlet Cookie
        Cookie cookie = new Cookie("access_token", "1-"+accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 1000000);
        response.addCookie(cookie);
        return Result.success("good",null);
    }











}
