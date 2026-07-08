package com.seek.food.user.Service.Impl;

import com.seek.food.config.NacosConfig.MQ.UserExchangeConfig;
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
import com.seek.food.util.FileUtil.FileSave;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RefreshScope
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserCaffeine userCaffeine;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserRedisKeyNameConfig userRedisKeyNameConfig;
    private final UserRedisKeyDurationConfig userRedisKeyDurationConfig;
    private final UserParamsRulesConfig userParamsRulesConfig;
    private final RabbitTemplate rabbitTemplate;
    private final UserExchangeConfig userExchangeConfig;
    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserCaffeine userCaffeine,StringRedisTemplate stringRedisTemplate
    ,UserRedisKeyNameConfig userRedisKeyNameConfig,UserRedisKeyDurationConfig userRedisKeyDurationConfig
    ,UserParamsRulesConfig userParamsRulesConfig,RabbitTemplate rabbitTemplate,UserExchangeConfig userExchangeConfig) {
        this.userMapper = userMapper;
        this.userCaffeine = userCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisKeyNameConfig = userRedisKeyNameConfig;
        this.userRedisKeyDurationConfig = userRedisKeyDurationConfig;
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.rabbitTemplate = rabbitTemplate;
        this.userExchangeConfig = userExchangeConfig;
    }
    // Bean 注入完成后再执行初始化
    @PostConstruct
    public void initPath() {
        //提前创建目录，后面头像保存无需再校验
        FileSave.createDestDir(userParamsRulesConfig.getHeaderImageDest());
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
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber)|| !userParamsRulesConfig.passwordCheck(newPassword))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //校验冷却期
        RedisUtil.checkCooldown(stringRedisTemplate,userRedisKeyNameConfig.getUpdatePasswordCooldown(),userRedisKeyDurationConfig.getUpdatePasswordCooldown());
        OPTUtil.checkOPT(stringRedisTemplate,userRedisKeyNameConfig.getUpdatePasswordOpt()+phoneNumber,opt);
        //如果mysql无目标数据会返回false
        if (!userMapper.updateUserPassword(phoneNumber,newPassword))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }

    //更改头像
    @Override
    public void updateUserHeader(MultipartFile file){
        //获取id
        long userId=TokenIdContext.getAndToLong();
        //校验id
        if (!userParamsRulesConfig.userIdCheck(userId))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //冷却期校验
        RedisUtil.checkCooldown(stringRedisTemplate,userRedisKeyNameConfig.getUpdateHeaderImageCooldown()+userId
                ,userRedisKeyDurationConfig.getUpdateHeaderImageCooldown());
        //快速保存
        String path=FileSave.quickCheckAndSaveFile(file,userParamsRulesConfig.getHeaderImageDest(), userParamsRulesConfig.getHeaderImageSize()
                , userParamsRulesConfig.getHeaderImageType());
        //检查是否成功
        if (!userMapper.updateUserHeader(userId,path))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //发送消息到mq中删除旧文件
        MQUtil.send(userExchangeConfig.getExchangeName(),userExchangeConfig.getUpdateFileUserQueue().getRoutingKey(),userId,rabbitTemplate,log);
    }

    //更改用户自身信息
    @Override
    public void updateUserMessage(UserDTO userDTO){
        //获取Id并检验
        long userId=TokenIdContext.getAndCheck(userParamsRulesConfig.getUserIdCheck());
        //检验性别，姓名，生日时期（出生日期）
        if (!userParamsRulesConfig.sexCheck(userDTO.getSex())||!userParamsRulesConfig.usernameCheck(userDTO.getUsername()) ||
        !userParamsRulesConfig.birthdayCheck(userDTO.getBirthday())) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate,userRedisKeyNameConfig.getUpdateMessageCooldown(),userRedisKeyDurationConfig.getUpdateMessageCooldown());
        userDTO.setUserId(userId);
        if(!userMapper.updateUserMessage(userDTO))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }




















}
