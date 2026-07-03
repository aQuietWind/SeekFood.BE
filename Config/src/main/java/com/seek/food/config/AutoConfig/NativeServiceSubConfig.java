package com.seek.food.config.AutoConfig;

import com.seek.food.config.NacosConfig.Gateway.GatewayBlackConfig;
import com.seek.food.config.NativeConfig.Handler.GlobalRequestExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({})
public class NativeServiceSubConfig {

    @Bean
    @ConditionalOnMissingBean // 业务可自定义覆盖
    public GatewayBlackConfig gatewayBlackConfig(GatewayBlackConfig gatewayBlackConfig) {
        return gatewayBlackConfig;
    }

}
