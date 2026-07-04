package com.seek.food.user.Service.Impl;

import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Mapper.UserMapper;
import com.seek.food.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public UserDTO getUserDetailMessage(long userId){
        return userMapper. getUserDetailMessage(userId);
    }

}
