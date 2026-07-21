package com.seek.food.fund.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyConfig;
import com.seek.food.dto.Fund.FundOrderRecordDTO;
import com.seek.food.dto.Fund.FundWithdrawRecordDTO;
import com.seek.food.fund.Caffeine.FundOrderRecordCaffeine;
import com.seek.food.fund.Mapper.FundOrderRecordMapper;
import com.seek.food.fund.Service.FundOrderRecordService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Redis.RedisUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundOrderRecordServiceImpl implements FundOrderRecordService {


    private final CommonParamRulesConfig commonParamRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final FundRedisKeyConfig fundRedisKeyConfig;
    private final FundOrderRecordMapper fundOrderRecordMapper;
    private final FundOrderRecordCaffeine fundOrderRecordCaffeine;

    public FundOrderRecordServiceImpl(CommonParamRulesConfig commonParamRulesConfig, StringRedisTemplate stringRedisTemplate, FundRedisKeyConfig fundRedisKeyConfig, FundOrderRecordMapper fundOrderRecordMapper, FundOrderRecordCaffeine fundOrderRecordCaffeine) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundRedisKeyConfig = fundRedisKeyConfig;
        this.fundOrderRecordMapper = fundOrderRecordMapper;
        this.fundOrderRecordCaffeine = fundOrderRecordCaffeine;
    }

    //批量获取预览信息
    @Override
    public List<FundOrderRecordDTO> getSimple(int start, int need){
        //检查需求量
        commonParamRulesConfig.needNumberCheck(need);
        //获取tokenId
        long userId= quickGetUserId();
        //检查冷却期
        quickCooldown(fundRedisKeyConfig.getFundGetSimpleWithdrawCooldown(),userId);
        //返回查询结果
        return fundOrderRecordMapper.getSimple(start,need,userId);
    }

    //获取详细信息
    @Override
    public FundOrderRecordDTO getDetail(long recordId){
        commonParamRulesConfig.commonIdCheck(recordId);
        long tokenId=TokenIdContext.getAndToLong();
        return fundOrderRecordCaffeine.getAndAutoLoad(recordId,stringRedisTemplate
                ,fundRedisKeyConfig.getFundOrderRecordCaffeineMessage().getRedisKey(recordId)
                ,fundRedisKeyConfig.getFundOrderRecordCaffeineMessage().getDuration()
                , FundOrderRecordDTO.class, k->fundOrderRecordMapper.getDetail(tokenId,recordId));
    }


    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private void quickCooldown(RedisKeyData keyData, long id) {
        RedisUtil.checkCooldown(stringRedisTemplate,keyData.getRedisKey(id), keyData.getDuration());
    }
}
