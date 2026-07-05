package com.seek.food.user.MQBind;

import com.seek.food.config.NacosConfig.MQ.UserExchangeConfig;
import com.seek.food.util.MQ.MQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserExchangeBind {

    private static final Logger log = LoggerFactory.getLogger(UserExchangeBind.class);
    private final UserExchangeConfig userExchangeConfig;
    @Autowired
    public UserExchangeBind(UserExchangeConfig userExchangeConfig) {
        this.userExchangeConfig = userExchangeConfig;
    }
    //创建一个用于用户注册消息投递的交换机
    @Bean
    public DirectExchange userExchange(){
        return new DirectExchange(userExchangeConfig.getExchangeName());
    }
    //用户注册队列
    @Bean
    public Queue userRegisterFundQueue(){
        return MQUtil.generateQuorumQueue(userExchangeConfig.getRegisterFundQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding userRegisterFundBinding(){
        return BindingBuilder.bind(userRegisterFundQueue()).to(userExchange()).with(userExchangeConfig.getRegisterFundQueue().getRoutingKey());
    }
    //用户文件更新队列
    @Bean
    public Queue updateFileUserQueue(){
        return MQUtil.generateQuorumQueue(userExchangeConfig.getUpdateFileUserQueue().getName());
    }
    //绑定交换机与队列
    @Bean
    public Binding userFileUserBinding(){
        return BindingBuilder.bind(updateFileUserQueue()).to(userExchange()).with(userExchangeConfig.getUpdateFileUserQueue().getRoutingKey());
    }













}
