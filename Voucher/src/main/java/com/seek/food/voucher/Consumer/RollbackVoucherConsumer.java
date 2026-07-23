package com.seek.food.voucher.Consumer;

import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Meal.MealRedisStreamConfig;
import com.seek.food.dto.Fund.FundOrderRecordMQDTO;
import com.seek.food.util.FileUtil.FileRemove;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.voucher.Mapper.VoucherConnectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RollbackVoucherConsumer {
    private final VoucherConnectionMapper voucherConnectionMapper;

    @Autowired
    public RollbackVoucherConsumer(VoucherConnectionMapper voucherConnectionMapper) {
        this.voucherConnectionMapper = voucherConnectionMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Order_Exchange_Rollback_Voucher_Queue)
    public void rollbackVoucherQueue(FundOrderRecordMQDTO record){
        voucherConnectionMapper.rollback(record.getOrderId());
    }
}
