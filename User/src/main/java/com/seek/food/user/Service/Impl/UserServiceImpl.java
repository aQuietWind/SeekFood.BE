package com.seek.food.user.Service.Impl;

import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.user.Caffeine.UserCaffeine;
import com.seek.food.user.Mapper.UserMapper;
import com.seek.food.user.Service.UserService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.Redis.RedisUtil;
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
        //验证id是否属于userId而不是别的
        if (!userParamsRulesConfig.userIdCheck(userId))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //从缓存中取出结果并且返回
        return userCaffeine.getAndAutoLoad(userId,stringRedisTemplate,userRedisKeyNameConfig.getCaffeineMessage()+userId
        ,userRedisKeyDurationConfig.getCaffeineMessage(),UserDTO.class,key->userMapper.getUserDetailMessage(userId));
    }

    //获取用户个人信息
    @Override
    public  UserDTO getUserSelfMessage(){
        long userId= TokenIdContext.getAndToLong();
        return getUserDetailMessage(userId);
    }

    //获取更改密码所需的验证码
    @Override
    public String updateUserPasswordGetOpt(String phoneNumber){
        //验证格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //获取验证码
        return OPTUtil.generateOPTAndRecord(stringRedisTemplate,userRedisKeyNameConfig.getUpdatePasswordOpt()+phoneNumber
                ,userRedisKeyDurationConfig.getOpt(),6);
    }

    //更改密码
    @Override
    public void updateUserPassword(String phoneNumber, String newPassword,String opt){
        //验证格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber)||!userParamsRulesConfig.passwordCheck(newPassword))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //校验冷却期
        RedisUtil.checkCooldown(stringRedisTemplate,userRedisKeyNameConfig.getUpdatePasswordCooldown(),userRedisKeyDurationConfig.getUpdatePasswordCooldown());
        OPTUtil.checkOPT(stringRedisTemplate,userRedisKeyNameConfig.getUpdatePasswordOpt()+phoneNumber,opt);
        //如果mysql无目标数据会返回false
        if (!userMapper.updateUserPassword(phoneNumber,newPassword))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }





















}
