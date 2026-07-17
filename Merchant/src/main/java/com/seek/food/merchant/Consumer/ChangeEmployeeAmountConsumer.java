package com.seek.food.merchant.Consumer;

import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.dto.Common.ChangeAmountDTO;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.merchant.Util.MerchantUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Slf4j
public class ChangeEmployeeAmountConsumer {
    private final MerchantMapper merchantMapper;
    @Autowired
    public ChangeEmployeeAmountConsumer(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Employee_Exchange_Delete_File_Queue)
    public void changeEmployeeAmountQueue(ChangeAmountDTO changeAmountDTO) {
        if (!merchantMapper.updateEmployeeAmount(changeAmountDTO.getId(),changeAmountDTO.getChangeNumber())){
            log.warn("merchantId:{} ,在增减职员数:{} 时,并未查询到该有效商家",changeAmountDTO.getId(),changeAmountDTO.getChangeNumber());
        }
    }




}
