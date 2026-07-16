package com.seek.food.config.AutoConfig;


import com.seek.food.config.NacosConfig.MQ.EmployeeExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.MerchantExchangeConfig;
import com.seek.food.config.NacosConfig.MQ.UserExchangeConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserExchangeConfig.class, MerchantExchangeConfig.class, EmployeeExchangeConfig.class})
public class MQSubConfig {

    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserExchangeConfig userExchangeConfig(UserExchangeConfig userExchangeConfig) {
        return userExchangeConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public MerchantExchangeConfig merchantExchangeConfig(MerchantExchangeConfig merchantExchangeConfig) {
        return merchantExchangeConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public EmployeeExchangeConfig employeeExchangeConfig(EmployeeExchangeConfig employeeExchangeConfig) {
        return employeeExchangeConfig;
    }

}
