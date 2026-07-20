package com.seek.food.config.NacosConfig.Fund;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Fund_Redis_Key_Config)
@Data
public class FundRedisKeyConfig {
    private RedisKeyData fundInsertCooldown;
    private RedisKeyData fundRechargeCooldown;
    private RedisKeyData fundWithdrawCooldown;
    private RedisKeyData fundDecreaseLock;
    private RedisKeyData fundCaffeineMessage;
}
