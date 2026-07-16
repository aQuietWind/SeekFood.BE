package com.seek.food.fund.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyConfig;
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
    private final StringRedisTemplate stringRedisTemplate;
    private final FundRedisKeyConfig fundRedisKeyConfig;
    private final FundMapper fundMapper;
    private final FundParamsRulesConfig fundParamsRulesConfig;
    @Autowired
    public RegisterFundConsumer(StringRedisTemplate stringRedisTemplate, FundRedisKeyConfig fundRedisKeyConfig, FundMapper fundMapper
    , FundParamsRulesConfig fundParamsRulesConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundRedisKeyConfig = fundRedisKeyConfig;
        this.fundMapper = fundMapper;
        this.fundParamsRulesConfig = fundParamsRulesConfig;
        //初始化id计数器
        stringRedisTemplate.opsForValue().setIfAbsent(fundRedisKeyConfig.getFundIdCount().getName(),""+fundParamsRulesConfig.getFundIdCapacity());
    }

    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Register_Fund_Queue)
    public void registerFundQueue(long accountId){
        try {
            fundMapper.insertFund(IdUtil.IdGenerateByIncrease(fundRedisKeyConfig.getFundIdCount().getName(), stringRedisTemplate),accountId);
        }catch (Exception e){
            log.error("account:{} ,新增Fund表数据时出现异常",accountId,e);
        }
    }
















}
