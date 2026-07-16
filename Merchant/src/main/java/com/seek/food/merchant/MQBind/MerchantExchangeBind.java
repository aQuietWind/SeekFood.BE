package com.seek.food.merchant.MQBind;

import com.seek.food.config.NacosConfig.MQ.MerchantExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MerchantExchangeBind {

    private final MerchantExchangeConfig merchantExchangeConfig;
    @Autowired
    public MerchantExchangeBind(MerchantExchangeConfig merchantExchangeConfig) {
        this.merchantExchangeConfig= merchantExchangeConfig;
    }
    //创建一个用于用户注册消息投递的交换机
    @Bean
    public DirectExchange merchantExchange() {
        return new DirectExchange(merchantExchangeConfig.getExchangeName());
    }
    //资金注册队列
    @Bean
    public Queue merchantRegisterFundQueue(){
        return MQUtil.generateQuorumQueue(merchantExchangeConfig.getRegisterFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding userRegisterFundBinding(Queue merchantRegisterFundQueue, DirectExchange merchantExchange){
        return BindingBuilder.bind(merchantRegisterFundQueue).to(merchantExchange).with(merchantExchangeConfig.getRegisterFundQueue().getRoutingKey());
    }
    //资金删除队列
    @Bean
    public Queue deleteFundQueue(){
        return MQUtil.generateQuorumQueue(merchantExchangeConfig.getDeleteFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteFundBinding(Queue deleteFundQueue, DirectExchange merchantExchange){
        return BindingBuilder.bind(deleteFundQueue).to(merchantExchange).with(merchantExchangeConfig.getDeleteFundQueue().getRoutingKey());
    }
    //文件删除队列
    @Bean
    public Queue deleteFileMerchantQueue(){
        return MQUtil.generateQuorumQueue(merchantExchangeConfig.getDeleteFileMerchantQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteFileMerchantBinding(Queue deleteFileMerchantQueue, DirectExchange merchantExchange){
        return BindingBuilder.bind(deleteFileMerchantQueue).to(merchantExchange).with(merchantExchangeConfig.getDeleteFileMerchantQueue().getRoutingKey());
    }
    //商家删除销毁队列
    @Bean
    public Queue deleteMerchantQueue(){
        return MQUtil.generateQuorumQueue(merchantExchangeConfig.getDeleteMerchantQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding deleteMerchantBinding(Queue deleteMerchantQueue, DirectExchange merchantExchange){
        return BindingBuilder.bind(deleteMerchantQueue).to(merchantExchange).with(merchantExchangeConfig.getDeleteMerchantQueue().getRoutingKey());
    }
    //es同步队列
    @Bean
    public Queue esSyncMerchantQueue(){
        return MQUtil.generateQuorumQueue(merchantExchangeConfig.getEsSyncMerchantQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding esSyncMerchantBinding(Queue esSyncMerchantQueue, DirectExchange merchantExchange){
        return BindingBuilder.bind(esSyncMerchantQueue).to(merchantExchange).with(merchantExchangeConfig.getEsSyncMerchantQueue().getRoutingKey());
    }
}