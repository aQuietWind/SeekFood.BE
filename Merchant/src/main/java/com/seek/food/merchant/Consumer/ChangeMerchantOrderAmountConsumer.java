package com.seek.food.merchant.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.dto.Common.ChangeAmountDTO;
import com.seek.food.merchant.Mapper.MerchantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ChangeMerchantOrderAmountConsumer {
    private final MerchantMapper merchantMapper;
    @Autowired
    public ChangeMerchantOrderAmountConsumer(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Order_Exchange_Change_Merchant_Order_Amount_Queue)
    public void changeMerchantOrderAmountQueue(ChangeAmountDTO changeAmountDTO) {
        if (!merchantMapper.updateOrderAmount(changeAmountDTO.getId(),changeAmountDTO.getChangeNumber())){
            log.warn("merchantId:{} ,在增减订单数:{} 时,并未查询到该有效商家",changeAmountDTO.getId(),changeAmountDTO.getChangeNumber());
        }
    }




}
