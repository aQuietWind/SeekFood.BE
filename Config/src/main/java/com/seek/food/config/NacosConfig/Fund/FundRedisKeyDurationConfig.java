package com.seek.food.config.NacosConfig.Fund;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Fund_Redis_Key_Duration_Config)
public class FundRedisKeyDurationConfig {
}
