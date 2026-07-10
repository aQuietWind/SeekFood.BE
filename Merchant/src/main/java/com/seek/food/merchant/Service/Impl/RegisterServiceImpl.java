package com.seek.food.merchant.Service.Impl;

import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.merchant.Mapper.RegisterMapper;
import com.seek.food.merchant.Service.RegisterService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.JWT.TokenUtil;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final RegisterMapper registerMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    public RegisterServiceImpl(RegisterMapper registerMapper,StringRedisTemplate stringRedisTemplate
    ,MerchantRedisKeyConfig merchantRedisKeyConfig,CommonParamRulesConfig commonParamRulesConfig) {
        this.registerMapper = registerMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        stringRedisTemplate.opsForValue().setIfAbsent(merchantRedisKeyConfig.getMerchantIdCount().getName(),
                ""+commonParamRulesConfig.getIdCapacity());
    }

    @Override
    public String getRegisterOpt(String phoneNumber){
        //检查手机号
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        //返回验证码
        return OPTUtil.generateOPTAndRecord(stringRedisTemplate,merchantRedisKeyConfig.getMerchantRegisterOpt().getName()+phoneNumber
        ,merchantRedisKeyConfig.getMerchantRegisterOpt().getDuration(),6);
    }
    @Override
    public void toRegister(String phoneNumber,String opt,String password){
        //检查信息
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        commonParamRulesConfig.passwordCheck(password);
        //检查验证码
        OPTUtil.checkOPT(stringRedisTemplate,merchantRedisKeyConfig.getMerchantRegisterOpt().getName()+phoneNumber,opt);
        //插入商家
        registerMapper.insertMerchant(IdUtil.IdGenerateByIncrease(merchantRedisKeyConfig.getMerchantIdCount().getName(),stringRedisTemplate)
                ,phoneNumber,password);
    }














}
