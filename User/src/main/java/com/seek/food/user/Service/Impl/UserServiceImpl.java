package com.seek.food.user.Service.Impl;

import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Caffeine.UserCaffeine;
import com.seek.food.user.Mapper.UserMapper;
import com.seek.food.user.Service.UserService;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserCaffeine userCaffeine;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserRedisKeyNameConfig userRedisKeyNameConfig;
    private final UserRedisKeyDurationConfig userRedisKeyDurationConfig;
    private final UserParamsRulesConfig userParamsRulesConfig;
    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserCaffeine userCaffeine,StringRedisTemplate stringRedisTemplate
    ,UserRedisKeyNameConfig userRedisKeyNameConfig,UserRedisKeyDurationConfig userRedisKeyDurationConfig
    ,UserParamsRulesConfig userParamsRulesConfig) {
        this.userMapper = userMapper;
        this.userCaffeine = userCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisKeyNameConfig = userRedisKeyNameConfig;
        this.userRedisKeyDurationConfig = userRedisKeyDurationConfig;
        this.userParamsRulesConfig = userParamsRulesConfig;
    }


    @Override
    public UserDTO getUserDetailMessage(long userId){
        if (!userParamsRulesConfig.userIdCheck(userId))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //从缓存中取出结果并且返回
        return userCaffeine.getAndAutoLoad(userId,stringRedisTemplate,userRedisKeyNameConfig.getCaffeineMessage()+userId
        ,userRedisKeyDurationConfig.getCaffeineMessage(),UserDTO.class,key->userMapper.getUserDetailMessage(userId));
    }

    @Override
    public  UserDTO getUserSelfMessage(){

        return
    }



















}
