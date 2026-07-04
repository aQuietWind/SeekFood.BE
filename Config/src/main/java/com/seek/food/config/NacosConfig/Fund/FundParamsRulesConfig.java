package com.seek.food.config.NacosConfig.Fund;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Fund_Params_Rules_Config)
public class FundParamsRulesConfig {
    private int fundIdStart;
    private long fundIdCapacity;

    public FundParamsRulesConfig() {
    }

    public FundParamsRulesConfig(int fundIdStart, long fundIdCapacity) {
        this.fundIdStart = fundIdStart;
        this.fundIdCapacity = fundIdCapacity;
    }

    //------------------------
    //校验参数
    public boolean fundIdCheck(long fundId) {
        return (fundId/fundIdCapacity)==fundIdStart;
    };

    /**
     * 获取
     * @return fundIdStart
     */
    public int getFundIdStart() {
        return fundIdStart;
    }

    /**
     * 设置
     * @param fundIdStart
     */
    public void setFundIdStart(int fundIdStart) {
        this.fundIdStart = fundIdStart;
    }

    /**
     * 获取
     * @return fundIdCapacity
     */
    public long getFundIdCapacity() {
        return fundIdCapacity;
    }

    /**
     * 设置
     * @param fundIdCapacity
     */
    public void setFundIdCapacity(long fundIdCapacity) {
        this.fundIdCapacity = fundIdCapacity;
    }

    public String toString() {
        return "FundParamsRulesConfig{fundIdStart = " + fundIdStart + ", fundIdCapacity = " + fundIdCapacity + "}";
    }
}
