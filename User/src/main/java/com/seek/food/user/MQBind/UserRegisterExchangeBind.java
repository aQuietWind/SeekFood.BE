package com.seek.food.user.MQBind;

import com.seek.food.config.NacosConfig.MQ.UserRegisterExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserRegisterExchangeBind {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterExchangeBind.class);
    private final UserRegisterExchangeConfig userRegisterExchangeConfig;
    @Autowired
    public UserRegisterExchangeBind(UserRegisterExchangeConfig userRegisterExchangeConfig) {
        this.userRegisterExchangeConfig = userRegisterExchangeConfig;
    }
    //创建一个用于用户注册消息投递的交换机
    @Bean
    public FanoutExchange userRegisterExchange(){
        return new FanoutExchange(userRegisterExchangeConfig.getExchangeName());
    }
    @Bean
    public Queue userRegisterFundQueue(){
        return MQUtil.generateQuorumQueue(userRegisterExchangeConfig.getFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding userRegisterFundBinding(){
        return BindingBuilder.bind(userRegisterFundQueue()).to(userRegisterExchange());
    }













}
