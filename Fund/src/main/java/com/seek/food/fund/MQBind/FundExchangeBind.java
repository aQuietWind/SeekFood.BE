package com.seek.food.fund.MQBind;

import com.seek.food.config.NacosConfig.MQ.DeadLetterExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.FundExchangeConfig;
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
public class FundExchangeBind {

    private final FundExchangeConfig fundExchangeConfig;
    private final DeadLetterExchangeConfig deadLetterExchangeConfig;
    @Autowired
    public FundExchangeBind(FundExchangeConfig fundExchangeConfig, DeadLetterExchangeConfig deadLetterExchangeConfig) {
        this.fundExchangeConfig= fundExchangeConfig;
        this.deadLetterExchangeConfig = deadLetterExchangeConfig;
    }
    //创建一个用于资金消息投递的交换机
    @Bean
    public DirectExchange fundExchange() {
        return new DirectExchange(fundExchangeConfig.getExchangeName());
    }



    //折扣券使用队列，也是开启订单确认的中间步骤
    @Bean
    public Queue voucherUseQueue(){
        return MQUtil.generateQuorumQueue(fundExchangeConfig.getVoucherUseQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding voucherUseBinding(Queue voucherUseQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(voucherUseQueue).to(fundExchange).with(fundExchangeConfig.getVoucherUseQueue().getRoutingKey());
    }

    //资金回滚队列，消费者在Fund模块，但是要监听Fund和Order模块
    @Bean
    public Queue rollbackFundQueue(){
        return MQUtil.generateQuorumQueue(fundExchangeConfig.getRollbackFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbackFundBinding(Queue rollbackFundQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(rollbackFundQueue).to(fundExchange).with(fundExchangeConfig.getRollbackFundQueue().getRoutingKey());
    }

    //折扣券回滚队列
    @Bean
    public Queue rollbakcVoucherQueue(){
        return MQUtil.generateQuorumQueue(fundExchangeConfig.getRollbackVoucherQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbakcVoucherBinding(Queue rollbakcVoucherQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(rollbakcVoucherQueue).to(fundExchange).with(fundExchangeConfig.getRollbackVoucherQueue().getRoutingKey());
    }
    //折扣券回滚队列
    @Bean
    public Queue refundQueue(){
        return MQUtil.generateQuorumQueue(fundExchangeConfig.getRefundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding refundBinding(Queue refundQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(refundQueue).to(fundExchange).with(fundExchangeConfig.getRefundQueue().getRoutingKey());
    }


    //延时回滚一切的死信队列,绑定死信交换机，同时监听FundExchange,但是消费者会出现在Order模块
    @Bean
    public Queue rollbackAllFundDeadLetterQueue(){
        return MQUtil.getDeadQuorumQueue(fundExchangeConfig.getRollbackAllFundDeadLetterQueue().getName()
                ,deadLetterExchangeConfig.getExchangeName(),deadLetterExchangeConfig.getRollbackAllFundImplQueue().getRoutingKey());
    }
    //绑定交换机与队列
    @Bean
    public Binding rollbackAllFundDeadLetterBinding(Queue rollbackAllFundDeadLetterQueue, DirectExchange fundExchange){
        return BindingBuilder.bind(rollbackAllFundDeadLetterQueue).to(fundExchange).with(fundExchangeConfig.getRollbackAllFundDeadLetterQueue().getRoutingKey());
    }

}