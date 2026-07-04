package com.seek.food.config.AutoConfig;


import com.seek.food.config.NacosConfig.MQ.UserRegisterExchangeConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserRegisterExchangeConfig.class})
public class MQSubConfig {

    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserRegisterExchangeConfig userRegisterExchangeConfig(UserRegisterExchangeConfig userRegisterExchangeConfig) {
        return userRegisterExchangeConfig;
    }

}
