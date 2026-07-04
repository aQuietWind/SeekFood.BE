package com.seek.food.config.NacosConfig.Common;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Common_Redis_Key)
public class CommonRedisKeyConfig {
    private String loginToken;

    public CommonRedisKeyConfig() {
    }

    public CommonRedisKeyConfig(String loginToken) {
        this.loginToken = loginToken;
    }

    /**
     * 获取
     * @return loginToken
     */
    public String getLoginToken() {
        return loginToken;
    }

    /**
     * 设置
     * @param loginToken
     */
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String toString() {
        return "CommonRedisKeyConfig{loginToken = " + loginToken + "}";
    }
}
