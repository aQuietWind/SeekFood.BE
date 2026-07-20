package com.seek.food.fund.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.fund.Mapper.FundMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FundOrderRecordConsumer {
    private FundMapper fundMapper;
    @Autowired
    public FundOrderRecordConsumer(FundMapper fundMapper) {
        this.fundMapper = fundMapper;
    }

    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Delete_Fund_Queue)
    public void deleteFundQueue(long accountId){
        if (!fundMapper.deleteFund(accountId)) log.error("accountId:{} ,资金删除失败",accountId);
    }




















}
