package com.seek.food.meal.MQBind;

import com.seek.food.config.NacosConfig.MQ.DeadLetterExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.MealExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MealExchangeBind {

    private final MealExchangeConfig mealExchangeConfig;
    private final DeadLetterExchangeConfig deadLetterExchangeConfig;
    @Autowired
    public MealExchangeBind(MealExchangeConfig mealExchangeConfig,DeadLetterExchangeConfig deadLetterExchangeConfig) {
        this.mealExchangeConfig= mealExchangeConfig;
        this.deadLetterExchangeConfig = deadLetterExchangeConfig;
    }
    //创建一个用于商家消息投递的交换机
    @Bean
    public DirectExchange mealExchange() {
        return new DirectExchange(mealExchangeConfig.getExchangeName());
    }
    //创建死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchangeConfig.getExchangeName());
    }
    //餐品死信队列
    @Bean
    public Queue deleteMealDeadLetterQueue(){
        return MQUtil.getDeadQuorumQueue(mealExchangeConfig.getDeleteMealDeadLetterQueue().getName(),deadLetterExchangeConfig.getExchangeName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteMealDeadLetterBinding(Queue deleteMealDeadLetterQueue, DirectExchange mealExchange){
        return BindingBuilder.bind(deleteMealDeadLetterQueue).to(mealExchange).with(mealExchangeConfig.getDeleteMealDeadLetterQueue().getRoutingKey());
    }
    //餐品删除队列
    @Bean
    public Queue deleteMealQueue(){
        return MQUtil.generateQuorumQueue(deadLetterExchangeConfig.getDeleteMealQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteMealBinding(Queue deleteMealQueue, DirectExchange deadLetterExchange){
        return BindingBuilder.bind(deleteMealQueue).to(deadLetterExchange).with(deadLetterExchangeConfig.getDeleteMealQueue().getRoutingKey());
    }
    //文件删除队列
    @Bean
    public Queue deleteFileMealQueue(){
        return MQUtil.generateQuorumQueue(mealExchangeConfig.getDeleteFileMealQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteFileMealBinding(Queue deleteFileMealQueue, DirectExchange mealExchange){
        return BindingBuilder.bind(deleteFileMealQueue).to(mealExchange).with(mealExchangeConfig.getDeleteFileMealQueue().getRoutingKey());
    }
}