package com.seek.food.meal.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Meal.MealParamsRulesConfig;
import com.seek.food.meal.Mapper.MealMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class DeleteAllMealConsumer {
    private final StringRedisTemplate stringRedisTemplate;
    private final MealMapper mealMapper;
    private final MealParamsRulesConfig mealParamsRulesConfig;
    @Autowired
    public DeleteAllMealConsumer(StringRedisTemplate stringRedisTemplate, MealMapper mealMapper
    , MealParamsRulesConfig mealParamsRulesConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.mealMapper = mealMapper;
        this.mealParamsRulesConfig = mealParamsRulesConfig;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_All_Employee_Queue)
    public void deleteAllEmployeeQueue(long merchantId){

    }















}
