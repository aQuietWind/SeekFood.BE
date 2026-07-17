package com.seek.food.meal.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class DeleteMealConsumer {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DeleteMealConsumer( StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_Merchant_Queue)
    public void deleteMerchantQueue(long merchantId){
    }









}
