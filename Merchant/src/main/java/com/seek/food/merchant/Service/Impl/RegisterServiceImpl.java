package com.seek.food.merchant.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.merchant.Mapper.RegisterMapper;
import com.seek.food.merchant.Service.RegisterService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.Es.EsUtil;
import com.seek.food.util.JWT.TokenUtil;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RefreshScope
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final RegisterMapper registerMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final ElasticsearchClient esClient;
    private final MerchantEsTableConfig merchantEsTableConfig;;
    public RegisterServiceImpl(RegisterMapper registerMapper,StringRedisTemplate stringRedisTemplate
    ,MerchantRedisKeyConfig merchantRedisKeyConfig,CommonParamRulesConfig commonParamRulesConfig,ElasticsearchClient esClient
    ,MerchantEsTableConfig merchantEsTableConfig) {
        this.registerMapper = registerMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.esClient=esClient;
        this.merchantEsTableConfig = merchantEsTableConfig;
        stringRedisTemplate.opsForValue().setIfAbsent(merchantRedisKeyConfig.getMerchantIdCount().getName(),
                ""+commonParamRulesConfig.getMerchantIdStart()*commonParamRulesConfig.getIdCapacity());
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
    @Transactional
    public void toRegister(String phoneNumber,String opt,String password) {
        //检查信息
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        commonParamRulesConfig.passwordCheck(password);
        //检查验证码
        OPTUtil.checkOPT(stringRedisTemplate,merchantRedisKeyConfig.getMerchantRegisterOpt().getName()+phoneNumber,opt);
        //获取id
        long merchantId=IdUtil.IdGenerateByIncrease(merchantRedisKeyConfig.getMerchantIdCount().getName(),stringRedisTemplate);
        //插入商家
        registerMapper.insertMerchant(merchantId,phoneNumber,password);
        //插入es
        EsUtil.quickInsert(esClient,merchantEsTableConfig.getIndexName(),merchantId, new MerchantEsDTO(merchantId,"商家"+merchantId
                        ,0,0,null,false));
    }














}
