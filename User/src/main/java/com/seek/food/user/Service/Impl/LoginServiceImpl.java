package com.seek.food.user.Service.Impl;

import com.seek.food.config.Data.JWTData;
import com.seek.food.config.NacosConfig.Common.CommonRedisKeyConfig;
import com.seek.food.config.NacosConfig.Common.JWTConfig;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.dto.User.UserDTO;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import com.seek.food.user.Mapper.LoginMapper;
import com.seek.food.user.Service.LoginService;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.JWT.TokenUtil;
import com.seek.food.util.Redis.RedisUtil;
import com.seek.food.util.TimeUtil.DurationUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class LoginServiceImpl implements LoginService {
    private final JWTData JWTUser;
    private final JWTConfig jwtConfig;
    private final CommonRedisKeyConfig commonRedisKeyConfig;
    private final LoginMapper loginMapper;
    private final UserParamsRulesConfig userParamsRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserRedisKeyNameConfig userRedisKeyNameConfig;
    private final UserRedisKeyDurationConfig userRedisKeyDurationConfig;
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    public LoginServiceImpl(JWTConfig jwtConfig, LoginMapper loginMapper, UserParamsRulesConfig userParamsRulesConfig
    ,StringRedisTemplate stringRedisTemplate, UserRedisKeyNameConfig userRedisKeyNameConfig, UserRedisKeyDurationConfig userRedisKeyDurationConfig
    ,CommonRedisKeyConfig commonRedisKeyConfig) {
        this.jwtConfig = jwtConfig;
        this.JWTUser = jwtConfig.getUser();
        this.commonRedisKeyConfig = commonRedisKeyConfig;
        this.loginMapper = loginMapper;
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisKeyNameConfig = userRedisKeyNameConfig;
        this.userRedisKeyDurationConfig = userRedisKeyDurationConfig;
    }

    @Override
    //获取登录所需验证码
    public String loginGetOpt(String phoneNumber){
        logger.info("phone number:{} ,尝试获取登录验证码",phoneNumber);
        //验证手机号
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //生产验证码
        String opt= OPTUtil.generateOPT(6);
        //存储验证码到redis，并检查是否已经存在验证码
        if(Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(
                userRedisKeyNameConfig.getLoginOpt() + phoneNumber,
                opt,
                DurationUtil.getSecondDuration(userRedisKeyDurationConfig.getOpt())))) return opt;
        else throw new BizException(ErrorCodeEnum.OPT_SURVIVE);
    }

    @Override
    //手机号与验证码登录
    public UserDTO login(String phoneNumber, String opt, HttpServletResponse response){
        //检验格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //检查验证码
        OPTUtil.checkOPT(stringRedisTemplate, userRedisKeyNameConfig.getLoginOpt() + phoneNumber, opt);
        //根据手机号获取目标
        return loginAndGetToken(loginMapper.getUserByPhoneNumber(phoneNumber),response);
    }

    //通过密码登录
    @Override
    public UserDTO loginByPassword(String phoneNumber, String password, HttpServletResponse response){
        //检验格式
        if (!userParamsRulesConfig.phoneNumberCheck(phoneNumber) ||!userParamsRulesConfig.passwordCheck(password))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //获取冷却时间
        if (Boolean.FALSE.equals(stringRedisTemplate.opsForValue().setIfAbsent(
                userRedisKeyNameConfig.getLoginPasswordCooldown() + phoneNumber, "true",
                DurationUtil.getSecondDuration(userRedisKeyDurationConfig.getLoginPasswordCooldown()))))throw new BizException(ErrorCodeEnum.REQUEST_IN_COOLDOWN);
        //验证登录
        return loginAndGetToken(loginMapper.getUserByPassword(phoneNumber, password),response);
    }

    //刷新token用
    @Override
    public void loginRefresh(HttpServletResponse response){
        long userId=TokenIdContext.getAndToLong();
        //防止别的商家等id请求token,导致误用密钥
        if (userParamsRulesConfig.userIdCheck(userId))throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        //检查冷却期，防止频繁刷新token
        RedisUtil.checkCooldown(stringRedisTemplate, userRedisKeyNameConfig.getLoginRefreshCooldown()+userId,userRedisKeyDurationConfig.getLoginRefreshCooldown());
        loginAndGetToken(TokenIdContext.getAndToLong(), response);
    }

    //发放登录信息
    private UserDTO loginAndGetToken(UserDTO user, HttpServletResponse response){
        if (user == null) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //获取token，并且放在请求头上
        getTokenByUtil(user.getUserId(),  response);
        return user;
    }

    //同样为发放登录消息，不过只需要id
    private void loginAndGetToken(long userId, HttpServletResponse response){
        getTokenByUtil(userId,response);
    }

    private void getTokenByUtil(long userId, HttpServletResponse response){
        //获取token，并且放在请求头上
        TokenUtil.getAndRecordToken(userId,response, JWTUser.getSecretKey()
                , JWTUser.getTokenDuration()
                , jwtConfig.getRequestTokenName()
                , JWTUser.getHeaderSign()
                , jwtConfig.getHeaderSeparator()
                , commonRedisKeyConfig.getLoginToken()
                , jwtConfig.getMaxStore()
                , stringRedisTemplate);
    }



















}
