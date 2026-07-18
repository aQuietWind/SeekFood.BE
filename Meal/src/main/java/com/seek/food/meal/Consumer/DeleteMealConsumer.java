package com.seek.food.meal.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.meal.Mapper.MealMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class DeleteMealConsumer {
    private final MealMapper mealMapper;

    @Autowired
    public DeleteMealConsumer(MealMapper mealMapper) {
        this.mealMapper = mealMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_Merchant_Queue)
    public void deleteMerchantQueue(long mealId, Message message){
        mealMapper.deleteMeal(mealId,message.getMessageProperties().getMessageId());
    }









}
