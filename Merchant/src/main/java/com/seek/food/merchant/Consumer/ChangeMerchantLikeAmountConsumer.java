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
public class ChangeMerchantLikeAmountConsumer {
    private final MerchantMapper merchantMapper;
    @Autowired
    public ChangeMerchantLikeAmountConsumer(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Employee_Exchange_Change_Amount_Employee_Queue)
    public void changeEmployeeAmountQueue(ChangeAmountDTO changeAmountDTO) {
        if (!merchantMapper.updateEmployeeAmount(changeAmountDTO.getId(),changeAmountDTO.getChangeNumber())){
            log.warn("merchantId:{} ,在增减职员数:{} 时,并未查询到该有效商家",changeAmountDTO.getId(),changeAmountDTO.getChangeNumber());
        }
    }




}
