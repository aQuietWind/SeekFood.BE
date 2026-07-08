package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Redis_Key_Name_Config)
@Data
public class UserRedisKeyNameConfig {
    private String registerOpt;
    private String loginOpt;
    private String deleteUserOpt;
    private String registerCooldown;
    private String updatePasswordOpt;
    private String loginRefreshCooldown;
    private String updateHeaderImageCooldown;
    private String updateMessageCooldown;
    private String updatePasswordCooldown;
    private String caffeineMessage;
    private String userIdCount;
    private String loginPasswordCooldown;
}
