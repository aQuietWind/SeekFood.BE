package com.seek.food.fund.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyConfig;
import com.seek.food.dto.Fund.FundRechargeRecordDTO;
import com.seek.food.fund.Mapper.FundMapper;
import com.seek.food.fund.Mapper.FundRechargeRecordMapper;
import com.seek.food.fund.Mapper.FundWithdrawRecordMapper;
import com.seek.food.fund.Service.FundRechargeRecordService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.Redis.RedisUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundRechargeRecordServiceImpl implements FundRechargeRecordService {
    private final FundMapper fundMapper;
    private final FundRechargeRecordMapper fundRechargeRecordMapper;
    private final FundWithdrawRecordMapper fundWithdrawRecordMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final FundParamsRulesConfig fundParamsRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final FundRedisKeyConfig fundRedisKeyConfig;

    public FundRechargeRecordServiceImpl(FundMapper fundMapper,FundRechargeRecordMapper fundRechargeRecordMapper,FundWithdrawRecordMapper fundWithdrawRecordMapper
            , CommonParamRulesConfig commonParamRulesConfig, FundParamsRulesConfig fundParamsRulesConfig, StringRedisTemplate stringRedisTemplate
            , FundRedisKeyConfig fundRedisKeyConfig) {
        this.fundMapper = fundMapper;
        this.fundRechargeRecordMapper = fundRechargeRecordMapper;
        this.fundWithdrawRecordMapper = fundWithdrawRecordMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.fundParamsRulesConfig = fundParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundRedisKeyConfig = fundRedisKeyConfig;
    }

    //查看简单的充值记录
    @Override
    public List<FundRechargeRecordDTO> getSimpleRechargeRecord(int start, int need){
        long tokenId=TokenIdContext.getAndToLong();
        quickCooldown(fundRedisKeyConfig.getFundGetSimpleRechargeCooldown(),tokenId);
        fundRechargeRecordMapper.getSimple(tokenId,start,need);
    }

    //查看详细的充值记录
    @Override
    public FundRechargeRecordDTO getDetailRechargeRecord(long recordId){
        long tokenId=TokenIdContext.getAndToLong();
        fundRechargeRecordMapper.getDetail(tokenId,recordId);
    }

    //充值
    @Override
    public void recharge(int rechargeAmount,String description){
        //检查金额大小与描述文本
        fundParamsRulesConfig.rechargeAmountCheck(rechargeAmount);
        fundParamsRulesConfig.descriptionCheck(description);
        //只允许用户充值
        long userId=quickGetUserId();
        //检查冷却
        quickCooldown(fundRedisKeyConfig.getFundRechargeCooldown(),userId);
        //写入MQ
        fundMapper.increaseFund(userId,rechargeAmount);
    }


    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }
    private long quickGetRiderAndMerchantId(){
        long tokenId=TokenIdContext.getAndToLong();
        int idStart= Math.toIntExact(tokenId / commonParamRulesConfig.getIdCapacity());
        if (idStart!=commonParamRulesConfig.getMerchantIdStart()&&idStart!=commonParamRulesConfig.getRiderIdStart()){
            throw new BizException(ErrorCodeEnum.PARAM_ERROR);
        }
        return tokenId;
    }

    private void quickCooldown(RedisKeyData keyData, long id) {
        RedisUtil.checkCooldown(stringRedisTemplate,keyData.getRedisKey(id), keyData.getDuration());
    }
}
