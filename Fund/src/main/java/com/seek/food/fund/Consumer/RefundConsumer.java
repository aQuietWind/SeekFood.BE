package com.seek.food.fund.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyConfig;
import com.seek.food.dto.Fund.FundOrderRecordMQDTO;
import com.seek.food.dto.Fund.FundOrderRefundRecordDTO;
import com.seek.food.fund.Caffeine.FundOrderRecordCaffeine;
import com.seek.food.fund.Mapper.FundOrderRecordMapper;
import com.seek.food.fund.Mapper.FundOrderRefundRecordMapper;
import com.seek.food.fund.Service.FundService;
import com.seek.food.util.CommonUtil.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefundConsumer {
    private final FundService fundService;
    private final FundOrderRefundRecordMapper fundOrderRefundRecordMapper;
    private final FundOrderRecordMapper fundOrderRecordMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final FundRedisKeyConfig fundRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final FundOrderRecordCaffeine fundOrderRecordCaffeine;

    @Autowired
    public RefundConsumer(FundService fundService, FundOrderRefundRecordMapper fundOrderRefundRecordMapper
            , FundOrderRecordMapper fundOrderRecordMapper, StringRedisTemplate stringRedisTemplate, FundRedisKeyConfig fundRedisKeyConfig
            , CommonParamRulesConfig commonParamRulesConfig, FundOrderRecordCaffeine fundOrderRecordCaffeine) {
        this.fundService = fundService;
        this.fundOrderRefundRecordMapper = fundOrderRefundRecordMapper;
        this.fundOrderRecordMapper = fundOrderRecordMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.fundRedisKeyConfig = fundRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.fundOrderRecordCaffeine = fundOrderRecordCaffeine;
        stringRedisTemplate.opsForValue().setIfAbsent(fundRedisKeyConfig.getFundOrderRefundRecordIdCount().getName(),""+commonParamRulesConfig.getIdCapacity());
    }

    @RabbitListener(queues = MQNameKeyEnum.Fund_Exchange_Rollback_Fund_Queue)
    public void refundQueue(FundOrderRecordMQDTO recordMQ) {
        //回滚资金
        fundService.increaseFund(recordMQ.getCost(), recordMQ.getAccountId());
        //在订单记录中确认退款
        fundOrderRecordMapper.ackRefund(recordMQ.getOrderId(), recordMQ.getAccountId());
        //删除缓存
        fundOrderRecordCaffeine.deleteAllCaffeine(recordMQ.getRecordId(), stringRedisTemplate
                , fundRedisKeyConfig.getFundOrderRecordCaffeineMessage().getRedisKey(recordMQ.getAccountId()));
        //新增退款记录
        fundOrderRefundRecordMapper.insertRecord(new FundOrderRefundRecordDTO(
                IdUtil.IdGenerateByIncrease(fundRedisKeyConfig.getFundOrderRecordIdCount().getName(), stringRedisTemplate)
                , recordMQ.getOrderId()
                , recordMQ.getAccountId()
                , "业务自动退回资金"
                , recordMQ.getCost()
                , 0
                , null));
    }

}
