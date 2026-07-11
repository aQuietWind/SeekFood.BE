package com.seek.food.merchant.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.*;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Caffeine.MerchantCaffeine;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.merchant.Service.MerchantService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.FileUtil.FileSave;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

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
    public MerchantServiceImpl(MerchantMapper merchantMapper, MerchantRedisKeyConfig merchantRedisKeyConfig, MerchantRedisStreamConfig merchantRedisStreamConfig
    , MerchantEsTableConfig merchantEsTableConfig, CommonParamRulesConfig commonParamRulesConfig, MerchantCaffeine merchantCaffeine
    , StringRedisTemplate stringRedisTemplate, ElasticsearchClient esClient, MerchantParamsRulesConfig merchantParamsRulesConfig) {
        this.merchantMapper = merchantMapper;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.merchantParamsRulesConfig = merchantParamsRulesConfig;
        this.merchantRedisStreamConfig = merchantRedisStreamConfig;
        this.merchantEsTableConfig = merchantEsTableConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.merchantCaffeine = merchantCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.esClient = esClient;
        //提前创建目录
        FileSave.createDestDir(merchantParamsRulesConfig.getMasterImageDest());
        FileSave.createDestDir(merchantParamsRulesConfig.getProofImageDest());
        FileSave.createDestDir(merchantParamsRulesConfig.getShowImageDest());
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

    @Override
    public void updateMerchantMaster(String merchantMasterName, String merchantMasterCode
            , @RequestBody MultipartFile masterImage){
        //获取商家id
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查是否还未设置过该信息
        if (RedisUtil.oftenGetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantMasterIsSet().getName(),merchantId,
                commonParamRulesConfig.getIdCapacity()))throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        //检查格式
        commonParamRulesConfig.personNameCheck(merchantMasterName);
        commonParamRulesConfig.codeCheck(merchantMasterCode);
        //检查冷却期，同时作为分布式锁防止多重设置
        RedisUtil.checkCooldown(stringRedisTemplate,merchantRedisKeyConfig.getMerchantUpdateMasterCooldown().getName()+merchantId,
                merchantRedisKeyConfig.getMerchantUpdateMasterCooldown().getDuration());
        //检查照片并且保存
        String addr=FileSave.quickCheckAndSaveFile(masterImage,merchantParamsRulesConfig.getMasterImageDest()
                ,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //写入mysql
        merchantCaffeine.updateAndRemoveCaffeine(merchantId,stringRedisTemplate,merchantRedisKeyConfig.getMerchantMessageCaffeine().getName()+merchantId
                , k->merchantMapper.setMerchantMaster(merchantId,merchantMasterName,merchantMasterCode,addr));
        //设置该信息已经被修改
        RedisUtil.oftenSetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantMasterIsSet().getName(),merchantId
                ,true,commonParamRulesConfig.getIdCapacity());
    }

    @Override
    @Transactional
    public void addMerchantProofImage(List<MultipartFile> merchantProofImages){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantAddProofCooldown().getName()+merchantId
                ,merchantRedisKeyConfig.getMerchantAddProofCooldown().getDuration());
        //检查文件并且保存文件
        FileSave.quickJudgeAndSaveFiles(merchantProofImages,merchantParamsRulesConfig.getProofImageDest(),
                commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType(),
                k->merchantMapper.addMerchantProofImage(k,merchantParamsRulesConfig.getProofImageNumberMax()));
    }

    @Override
    public void removeMerchantProofImage(int index){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantRemoveProofCooldown().getName()+merchantId
                ,merchantRedisKeyConfig.getMerchantAddProofCooldown().getDuration());
        merchantMapper.getMerchantProofImageByIndex();
    }

    @Override
    public void updateMerchantProofImage(MultipartFile merchantProofImage,int index){

    }












}
