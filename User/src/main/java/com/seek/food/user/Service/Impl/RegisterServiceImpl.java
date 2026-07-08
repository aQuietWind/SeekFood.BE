package com.seek.food.user.Service.Impl;

import com.seek.food.config.NacosConfig.MQ.UserExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.user.Mapper.RegisterMapper;
import com.seek.food.user.Service.RegisterService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.OPT.OPTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final UserExchangeConfig userExchangeConfig;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Autowired
    public RegisterServiceImpl(UserParamsRulesConfig userParamsRulesConfig, StringRedisTemplate stringRedisTemplate
    , UserRedisKeyNameConfig userRedisKeyNameConfig, UserRedisKeyDurationConfig userRedisKeyDurationConfig
    , RegisterMapper registerMapper , UserExchangeConfig userExchangeConfig, RabbitTemplate rabbitTemplate) {
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisKeyNameConfig = userRedisKeyNameConfig;
        this.userRedisKeyDurationConfig = userRedisKeyDurationConfig;
        this.registerMapper = registerMapper;
        this.userExchangeConfig = userExchangeConfig;
        this.rabbitTemplate = rabbitTemplate;
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
        return OPTUtil.generateOPTAndRecord(stringRedisTemplate,userRedisKeyNameConfig.getRegisterOpt() + phoneNumber
                ,userRedisKeyDurationConfig.getOpt(),6);
    }

    //注册用户
    @Override
    public void registerUser(String phoneNumber, String password, String opt) {
        //检验格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber) || !userParamsRulesConfig.passwordCheck(password))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //检验验证码
        OPTUtil.checkOPT(stringRedisTemplate,userRedisKeyNameConfig.getRegisterOpt() + phoneNumber,opt);
        long userId=IdUtil.IdGenerateByIncrease(userRedisKeyNameConfig.getUserIdCount(),stringRedisTemplate);
        //写入mysql,失败会报错
        registerMapper.insertUser(userId,phoneNumber,password);
        MQUtil.send(userExchangeConfig.getExchangeName(),userExchangeConfig.getRegisterFundQueue().getRoutingKey(),userId,rabbitTemplate,logger);
        logger.info("phone number:{} ,成功注册用户",phoneNumber);
    }








}
