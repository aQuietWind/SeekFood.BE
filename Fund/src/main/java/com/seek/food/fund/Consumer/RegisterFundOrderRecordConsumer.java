package com.seek.food.fund.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.MQ.DeadLetterExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.FundExchangeConfig;
import com.seek.food.dto.Fund.FundOrderRecordDTO;
import com.seek.food.fund.Mapper.FundMapper;
import com.seek.food.fund.Mapper.FundOrderRecordMapper;
import com.seek.food.util.MQ.MQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterFundOrderRecordConsumer {

    private final FundOrderRecordMapper fundOrderRecordMapper;
    private final FundParamsRulesConfig fundParamsRulesConfig;
    private final FundExchangeConfig fundExchangeConfig;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RegisterFundOrderRecordConsumer(FundOrderRecordMapper fundOrderRecordMapper, FundParamsRulesConfig fundParamsRulesConfig
            , FundExchangeConfig fundExchangeConfig, RabbitTemplate rabbitTemplate) {
        this.fundOrderRecordMapper = fundOrderRecordMapper;
        this.fundParamsRulesConfig = fundParamsRulesConfig;
        this.fundExchangeConfig = fundExchangeConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = MQNameKeyEnum.Order_Exchange_Register_Fund_Order_Record_Queue)
    public void registerFundOrderRecordQueue(FundOrderRecordDTO record) {
        //设置期限
        record.setDeadline(fundParamsRulesConfig.getDeadline());
        record.setAbleRollbackTime(fundParamsRulesConfig.getRollbackTime());
        //新增该记录
        fundOrderRecordMapper.insertRecord(record);
        //发送一条延时回滚消息
        MQUtil.sendWithTLL(fundExchangeConfig.getExchangeName(),fundExchangeConfig.getRollbackAllFundDeadLetterQueue().getRoutingKey()
        ,record.getOrderId(),rabbitTemplate,MQUtil.minuteToMillis(fundParamsRulesConfig.getRollbackMinuteMax()));
    }




















}
