package com.seek.food.user.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Enum.RequestPathEnum;
import com.seek.food.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(RequestPathEnum.User_Get_Detail)
    public Result<UserDTO> getUserDetailMessage(long userId){

        return Result.success(userService.getUserDetailMessage(userId));
    }

































}

