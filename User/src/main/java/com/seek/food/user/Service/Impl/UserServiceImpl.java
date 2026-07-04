package com.seek.food.user.Service.Impl;

import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Caffeine.UserCaffeine;
import com.seek.food.user.Mapper.UserMapper;
import com.seek.food.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserCaffeine userCaffeine;
    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserCaffeine userCaffeine) {
        this.userMapper = userMapper;
        this.userCaffeine = userCaffeine;
    }


    @Override
    public UserDTO getUserDetailMessage(long userId){
        userCaffeine.get(userId,key->{

        })
        return userMapper. getUserDetailMessage(userId);
    }

}
