package com.seek.food.user.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Config.NacosConfig.JWTConfig;
import com.seek.food.user.Enum.RequestPathEnum;
import com.seek.food.user.Service.LoginService;
import com.seek.food.util.JWT.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(RequestPathEnum.Login)
public class LoginController {
    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(RequestPathEnum.Register_Opt)
    public Result<String> loginGetOpt(String phoneNumber) {
        return Result.success(loginService.loginGetOpt(phoneNumber));
    }


    @GetMapping
    public Result<UserDTO> login(String phoneNumber, String opt, HttpServletResponse response) {
        return Result.success(loginService.login(phoneNumber,opt,response));
    }

    @GetMapping(RequestPathEnum.Login_Password)
    public Result<UserDTO> loginPassword(String phoneNumber, String password, HttpServletResponse response) {
        return Result.success(loginService.loginByPassword(phoneNumber,password,response));
    }











}
