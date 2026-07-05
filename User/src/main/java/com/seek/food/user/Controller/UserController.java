package com.seek.food.user.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Enum.RequestPathEnum;
import com.seek.food.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.User)
public class UserController{
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //获取他人的详细信息
    @GetMapping(RequestPathEnum.User_Get_Detail)
    public Result<UserDTO> getUserDetailMessage(long userId){
        return Result.success(userService.getUserDetailMessage(userId));
    }
    //获取用户自身的信息
    @GetMapping(RequestPathEnum.User_Get_Self)
    public Result<UserDTO> getUserSelfMessage(){
        return Result.success(userService.getUserSelfMessage());
    }
    //获取验证码以更改密码,且可用于用户忘记密码
    @GetMapping(RequestPathEnum.User_Update_Password)
    public Result<String> updateUserPasswordGetOpt(String phoneNumber){
        return Result.success(userService.updateUserPasswordGetOpt(phoneNumber));
    }
    //更改密码
    @PutMapping(RequestPathEnum.User_Update_Password)
    public Result<Void> updateUserPassword(String phoneNumber,String newPassword,String opt){
        userService.updateUserPassword(phoneNumber,newPassword,opt);
        return Result.success();
    }

































}

