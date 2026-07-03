package com.seek.food.user.Service.Impl;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.user.Mapper.RegisterMapper;
import com.seek.food.user.Service.RegisterService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.TimeUtil.DurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RefreshScope
public class RegisterServiceImpl implements RegisterService {
    private final UserParamsRulesConfig userParamsRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserRedisKeyNameConfig userRedisKeyNameConfig;
    private final UserRedisKeyDurationConfig userRedisKeyDurationConfig;
    private final RegisterMapper registerMapper;
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Autowired
    public RegisterServiceImpl(UserParamsRulesConfig userParamsRulesConfig, StringRedisTemplate stringRedisTemplate
    , UserRedisKeyNameConfig userRedisKeyNameConfig, UserRedisKeyDurationConfig userRedisKeyDurationConfig
    , RegisterMapper registerMapper) {
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisKeyNameConfig = userRedisKeyNameConfig;
        this.userRedisKeyDurationConfig = userRedisKeyDurationConfig;
        this.registerMapper = registerMapper;
        //初始化userIdCount
        stringRedisTemplate.opsForValue().setIfAbsent(userRedisKeyNameConfig.getUserIdCount(),
                ""+userParamsRulesConfig.getUserIdCapacity());
    }


    //获取注册所需的验证码
    @Override
    public String registerGetOpt(String phoneNumber) {
        logger.info("phone number:{} ,进行注册获取验证码",phoneNumber);
        //验证手机号
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //生产验证码
        String opt= OPTUtil.generateOPT(6);
        //存储验证码到redis，并检查是否已经存在验证码
        if(Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(
                userRedisKeyNameConfig.getRegisterOpt() + phoneNumber,
                opt,
                DurationUtil.getSecondDuration(userRedisKeyDurationConfig.getOpt())))) return opt;
        else throw new BizException(ErrorCodeEnum.OPT_SURVIVE);
    }

    //注册用户
    @Override
    public void registerUser(String phoneNumber, String password, String opt) {
        //检验格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber) ||!userParamsRulesConfig.passwordCheck(password))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //检验验证码
        OPTUtil.checkOPT(stringRedisTemplate,userRedisKeyNameConfig.getRegisterOpt() + phoneNumber,opt);
        //写入mysql,失败会报错
        registerMapper.insertUser(IdUtil.IdGenerateByIncrease(userRedisKeyNameConfig.getUserIdCount(),stringRedisTemplate),phoneNumber,password);
        logger.info("phone number:{} ,成功注册用户",phoneNumber);
    }








}
