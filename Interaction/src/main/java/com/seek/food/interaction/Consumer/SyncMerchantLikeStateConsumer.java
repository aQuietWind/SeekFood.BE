package com.seek.food.interaction.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.dto.Common.ChangeAmountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SyncMerchantLikeStateConsumer {
    private final MerchantMapper merchantMapper;
    @Autowired
    public SyncMerchantLikeStateConsumer(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Employee_Exchange_Change_Amount_Employee_Queue)
    public void changeEmployeeAmountQueue(ChangeAmountDTO changeAmountDTO) {
        merchantMapper.updateEmployeeAmount(changeAmountDTO.getId(),changeAmountDTO.getChangeNumber());
    }




}
