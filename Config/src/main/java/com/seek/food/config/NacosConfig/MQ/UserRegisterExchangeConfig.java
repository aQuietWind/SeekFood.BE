package com.seek.food.config.NacosConfig.MQ;


import com.seek.food.config.Data.QueueData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Register_Exchange_Config)
public class UserRegisterExchangeConfig {
    private String type;
    private String exchangeName;
    private QueueData fundQueue;

    public UserRegisterExchangeConfig() {
    }

    public UserRegisterExchangeConfig(String type, String exchangeName, QueueData fundQueue) {
        this.type = type;
        this.exchangeName = exchangeName;
        this.fundQueue = fundQueue;
    }

    /**
     * 获取
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取
     * @return exchangeName
     */
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     * 设置
     * @param exchangeName
     */
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    /**
     * 获取
     * @return fundQueue
     */
    public QueueData getFundQueue() {
        return fundQueue;
    }

    /**
     * 设置
     * @param fundQueue
     */
    public void setFundQueue(QueueData fundQueue) {
        this.fundQueue = fundQueue;
    }

    public String toString() {
        return "UserRegisterExchangeConfig{type = " + type + ", exchangeName = " + exchangeName + ", fundQueue = " + fundQueue.toString() + "}";
    }
}
