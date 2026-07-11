package com.seek.food.merchant.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.*;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Caffeine.MerchantCaffeine;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.merchant.Service.MerchantService;
import com.seek.food.util.Context.TokenIdContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    private final MerchantMapper merchantMapper;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final MerchantParamsRulesConfig merchantParamsRulesConfig;
    private final MerchantRedisStreamConfig merchantRedisStreamConfig;
    private final MerchantEsTableConfig merchantEsTableConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final MerchantCaffeine merchantCaffeine;
    private final StringRedisTemplate stringRedisTemplate;
    private final ElasticsearchClient esClient;
    @Autowired
    public MerchantServiceImpl(MerchantMapper merchantMapper,MerchantRedisKeyConfig merchantRedisKeyConfig
    ,MerchantParamsRulesConfig merchantParamsRulesConfig,MerchantRedisStreamConfig merchantRedisStreamConfig
    ,MerchantEsTableConfig merchantEsTableConfig,CommonParamRulesConfig commonParamRulesConfig
    ,MerchantCaffeine merchantCaffeine,StringRedisTemplate stringRedisTemplate,ElasticsearchClient esClient) {
        this.merchantMapper = merchantMapper;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.merchantParamsRulesConfig = merchantParamsRulesConfig;
        this.merchantRedisStreamConfig = merchantRedisStreamConfig;
        this.merchantEsTableConfig = merchantEsTableConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.merchantCaffeine = merchantCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.esClient = esClient;
    }

    @Override
    public MerchantDTO getMerchantDetail(long merchantId){
        commonParamRulesConfig.merchantIdCheck(merchantId);
        return merchantCaffeine.getAndAutoLoad(merchantId,stringRedisTemplate,merchantRedisKeyConfig.getMerchantMessageCaffeine().getName()+merchantId,
                merchantRedisKeyConfig.getMerchantMessageCaffeine().getDuration(),MerchantDTO.class,k->merchantMapper.getMerchantById(merchantId));
    }

    @Override
    public MerchantDTO getMerchantSelf(){
        long merchantId= TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        return getMerchantDetail(merchantId);
    }













}
