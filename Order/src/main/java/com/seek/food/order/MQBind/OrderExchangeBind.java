package com.seek.food.order.MQBind;

import com.seek.food.config.NacosConfig.MQ.DeadLetterExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.MealExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.OrderExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderExchangeBind {

    private final OrderExchangeConfig orderExchangeConfig;
    private final DeadLetterExchangeConfig deadLetterExchangeConfig;
    @Autowired
    public OrderExchangeBind(OrderExchangeConfig orderExchangeConfig, DeadLetterExchangeConfig deadLetterExchangeConfig) {
        this.orderExchangeConfig= orderExchangeConfig;
        this.deadLetterExchangeConfig = deadLetterExchangeConfig;
    }
    //创建一个用于该模块消息投递的交换机
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(orderExchangeConfig.getExchangeName());
    }



    //用于注册资金订单记录的队列
    @Bean
    public Queue registerFundOrderRecordQueue(){
        return MQUtil.generateQuorumQueue(orderExchangeConfig.getRegisterFundOrderRecordQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding registerFundOrderRecordBinding(Queue registerFundOrderRecordQueue, DirectExchange orderExchange){
        return BindingBuilder.bind(registerFundOrderRecordQueue).to(orderExchange).with(orderExchangeConfig.getRegisterFundOrderRecordQueue().getRoutingKey());
    }

    //用于回滚优惠券的队列
    @Bean
    public Queue rollbackVoucherQueue(){
        return MQUtil.generateQuorumQueue(orderExchangeConfig.getRollbackVoucherQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbackVoucherBinding(Queue rollbackVoucherQueue, DirectExchange orderExchange){
        return BindingBuilder.bind(rollbackVoucherQueue).to(orderExchange).with(orderExchangeConfig.getRollbackVoucherQueue().getRoutingKey());
    }

    //用于回滚资金的队列
    @Bean
    public Queue rollbackFundQueue(){
        return MQUtil.generateQuorumQueue(orderExchangeConfig.getRollbackFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbackFundBinding(Queue rollbackFundQueue, DirectExchange orderExchange){
        return BindingBuilder.bind(rollbackFundQueue).to(orderExchange).with(orderExchangeConfig.getRollbackFundQueue().getRoutingKey());
    }

    //延时回滚一切的死信队列,绑定死信交换机，同时监听FundExchange与OrderExchange,但是实现的消费者会出现在Order模块
    @Bean
    public Queue rollbackAllFundDeadLetterQueue(){
        return MQUtil.getDeadQuorumQueue(orderExchangeConfig.getRollbackAllFundDeadLetterQueue().getName()
                ,deadLetterExchangeConfig.getExchangeName(),deadLetterExchangeConfig.getRollbackAllFundImplQueue().getRoutingKey());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbackAllFundDeadLetterBinding(Queue rollbackAllFundDeadLetterQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(rollbackAllFundDeadLetterQueue).to(fundExchange).with(orderExchangeConfig.getRollbackAllFundDeadLetterQueue().getRoutingKey());
    }
}