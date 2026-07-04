package com.seek.food.config.AutoConfig;

import com.seek.food.config.NacosConfig.Fund.FundParamsRulesConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.Fund.FundRedisKeyNameConfig;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyDurationConfig;
import com.seek.food.config.NacosConfig.User.UserRedisKeyNameConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({FundParamsRulesConfig.class, FundRedisKeyDurationConfig.class, FundRedisKeyNameConfig.class})
public class FundSubConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public FundParamsRulesConfig fundParamsRulesConfig(FundParamsRulesConfig fundParamsRulesConfig) {
        return fundParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public FundRedisKeyDurationConfig fundRedisKeyDurationConfig(FundRedisKeyDurationConfig fundRedisKeyDurationConfig) {
        return fundRedisKeyDurationConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public FundRedisKeyNameConfig fundRedisKeyNameConfig(FundRedisKeyNameConfig fundRedisKeyNameConfig) {
        return fundRedisKeyNameConfig;
    }











}
