package com.seek.food.fund.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyConfig;
import com.seek.food.fund.Mapper.FundMapper;
import com.seek.food.fund.Service.FundService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.Redis.RedisUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FundServiceImpl implements FundService {

    private final FundMapper fundMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final FundParamsRulesConfig fundParamsRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final FundRedisKeyConfig fundRedisKeyConfig;

    public FundServiceImpl(FundMapper fundMapper, CommonParamRulesConfig commonParamRulesConfig, FundParamsRulesConfig fundParamsRulesConfig, StringRedisTemplate stringRedisTemplate, FundRedisKeyConfig fundRedisKeyConfig) {
        this.fundMapper = fundMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.fundParamsRulesConfig = fundParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundRedisKeyConfig = fundRedisKeyConfig;
    }

    //充值
    @Override
    public void recharge(int rechargeAmount){
        //检查金额大小
        fundParamsRulesConfig.fundRechargeAmountCheck(rechargeAmount);
        //只允许用户充值
        long userId=quickGetUserId();
        //检查冷却
        quickCooldown(fundRedisKeyConfig.getFundRechargeCooldown(),userId);
        //写入MQ
        fundMapper.increaseFund(userId,rechargeAmount);
    }

    //提现
    @Override
    public void withdraw(int withdrawAmount){
        //检查金额大小
        fundParamsRulesConfig.fundWithdrawAmountCheck(withdrawAmount);
        //只允许商家和骑手提现
        long tokenId=quickGetRiderAndMerchantId();
        //检查冷却
        quickCooldown(fundRedisKeyConfig.getFundWithdrawCooldown(),tokenId);
        //写入MQ
        fundMapper.decreaseFund(tokenId,withdrawAmount);
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

    private void quickCooldown(RedisKeyData keyData,long id) {
        RedisUtil.checkCooldown(stringRedisTemplate,keyData.getRedisKey(id), keyData.getDuration());
    }
}
