package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Redis_Key_Duration_Config)
@Data
public class UserRedisKeyDurationConfig {
    private long registerCooldown;
    private long opt;
    private long updateHeaderImageCooldown;
    private long updateMessageCooldown;
    private long updatePasswordCooldown;
    private long caffeineMessage;
    private long loginPasswordCooldown;
    private long loginRefreshCooldown;
}
