package com.seek.food.config.NacosConfig.Fund;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Fund_Redis_Key_Name_Config)
public class FundRedisKeyNameConfig {
    private String fundIdCount;

    public FundRedisKeyNameConfig() {
    }

    public FundRedisKeyNameConfig(String fundIdCount) {
        this.fundIdCount = fundIdCount;
    }

    /**
     * 获取
     * @return fundIdCount
     */
    public String getFundIdCount() {
        return fundIdCount;
    }

    /**
     * 设置
     * @param fundIdCount
     */
    public void setFundIdCount(String fundIdCount) {
        this.fundIdCount = fundIdCount;
    }

    public String toString() {
        return "FundRedisKeyNameConfig{fundIdCount = " + fundIdCount + "}";
    }
}
