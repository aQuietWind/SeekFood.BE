package com.seek.food.config.AutoConfig;

import com.seek.food.config.NacosConfig.Merchant.MerchantCaffeineConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisStreamConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRulesConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({MerchantCaffeineConfig.class, MerchantRedisKeyConfig.class, MerchantRedisStreamConfig.class
        , MerchantRulesConfig.class})
public class MerchantSubConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public MerchantCaffeineConfig merchantCaffeineConfig(MerchantCaffeineConfig merchantCaffeineConfig) {
        return merchantCaffeineConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public MerchantRedisKeyConfig merchantRedisKeyConfig(MerchantRedisKeyConfig merchantRedisKeyConfig) {
        return merchantRedisKeyConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public MerchantRedisStreamConfig merchantRedisStreamConfig(MerchantRedisStreamConfig merchantRedisStreamConfig) {
        return merchantRedisStreamConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public MerchantRulesConfig merchantRulesConfig(MerchantRulesConfig merchantRulesConfig) {
        return merchantRulesConfig;
    }
































}
