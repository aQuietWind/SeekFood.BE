package com.seek.food.config.NativeConfig.MQConfig;


import com.seek.food.config.NacosConfig.MQ.ErrorExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// config模块 MQCommonConfig
@Configuration
// 仅普通Servlet微服务生效，WebFlux网关不会实例化这个类
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MQErrorConfig {
    private final ErrorExchangeConfig errorExchangeConfig;
    @Autowired
    public MQErrorConfig(ErrorExchangeConfig errorExchangeConfig) {
        this.errorExchangeConfig = errorExchangeConfig;
    }
    //创建一个用于失败消息接受的队列
    @Bean
    public Queue errorQueue(){
        return MQUtil.generateQuorumQueue(errorExchangeConfig.getErrorQueue().getName());
    }
    //创建一个用于失败消息投递的交换机
    @Bean
    public FanoutExchange errorExchange(){
        return new FanoutExchange(errorExchangeConfig.getExchangeName());
    }
    //绑定交换机与队列
    @Bean
    public Binding errorBinding(){
        return BindingBuilder.bind(errorQueue()).to(errorExchange());
    }
    //该处理器会自动覆盖原先的RejectAndDontRequeueRecoverer处理器
    @Bean
    public MessageRecoverer recoverer(RabbitTemplate rabbitTemplate){
        //声明目标交换机以及key，返回该处理器
        return new RepublishMessageRecoverer(rabbitTemplate, errorExchangeConfig.getExchangeName());
    }
}