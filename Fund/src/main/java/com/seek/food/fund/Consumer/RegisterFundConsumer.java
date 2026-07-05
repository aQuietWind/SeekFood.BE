package com.seek.food.fund.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyNameConfig;
import com.seek.food.dto.Fund.FundDTO;
import com.seek.food.fund.Mapper.FundMapper;
import com.seek.food.util.CommonUtil.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterFundConsumer {
    private StringRedisTemplate stringRedisTemplate;
    private FundParamsRulesConfig fundParamsRulesConfig;
    private FundRedisKeyNameConfig fundRedisKeyNameConfig;
    private FundMapper fundMapper;
    @Autowired
    public RegisterFundConsumer(StringRedisTemplate stringRedisTemplate, FundParamsRulesConfig fundParamsRulesConfig
            , FundRedisKeyNameConfig fundRedisKeyNameConfig, FundMapper fundMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundParamsRulesConfig = fundParamsRulesConfig;
        this.fundRedisKeyNameConfig = fundRedisKeyNameConfig;
        this.fundMapper = fundMapper;
        //初始化id计数器
        stringRedisTemplate.opsForValue().setIfAbsent(fundRedisKeyNameConfig.getFundIdCount(),""+fundParamsRulesConfig.getFundIdCapacity());
    }

    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Register_Fund_Queue)
    public void registerFundQueue(long userId){
            FundDTO fund = new FundDTO(IdUtil.IdGenerateByIncrease(fundRedisKeyNameConfig.getFundIdCount(), stringRedisTemplate),
                    userId, null, null, null);
            fundMapper.insertFund(fund);
    }
















}
