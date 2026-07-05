package com.seek.food.config.AutoConfig;

import com.seek.food.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserParamsRulesConfig.class, UserRedisKeyDurationConfig.class, UserRedisKeyNameConfig.class,UserCaffeineConfig.class})
public class UserSubConfig {
    @Bean
    @ConditionalOnMissingBean // 业务可自定义覆盖
    @Lazy // 用到才实例化，启动不创建对象
    public UserParamsRulesConfig userParamsRulesConfig(UserParamsRulesConfig userParamsRulesConfig) {
        return userParamsRulesConfig;
    }
    @Bean
    @ConditionalOnMissingBean // 业务可自定义覆盖
    @Lazy // 用到才实例化，启动不创建对象
    public UserRedisKeyDurationConfig userRedisKeyDurationConfig(UserRedisKeyDurationConfig userRedisKeyDurationConfig) {
        return userRedisKeyDurationConfig;
    }
    @Bean
    @ConditionalOnMissingBean // 业务可自定义覆盖
    @Lazy // 用到才实例化，启动不创建对象
    public UserRedisKeyNameConfig userRedisKeyNameConfig(UserRedisKeyNameConfig userRedisKeyNameConfig) {
        return userRedisKeyNameConfig;
    }
    @Bean
    @ConditionalOnMissingBean // 业务可自定义覆盖
    @Lazy // 用到才实例化，启动不创建对象
    public UserCaffeineConfig userCaffeineConfig(UserCaffeineConfig userCaffeineConfig) {
        return userCaffeineConfig;
    }












}
