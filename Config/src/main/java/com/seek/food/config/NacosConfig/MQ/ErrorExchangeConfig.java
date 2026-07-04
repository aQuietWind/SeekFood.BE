package com.seek.food.config.NacosConfig.MQ;

import com.seek.food.config.Data.QueueData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Error_Exchange_Config)
public class ErrorExchangeConfig {
    private String type;
    private String exchangeName;
    private QueueData errorQueue;

    public ErrorExchangeConfig() {
    }

    public ErrorExchangeConfig(String type, String exchangeName, QueueData errorQueue) {
        this.type = type;
        this.exchangeName = exchangeName;
        this.errorQueue = errorQueue;
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
     * @return errorQueue
     */
    public QueueData getErrorQueue() {
        return errorQueue;
    }

    /**
     * 设置
     * @param errorQueue
     */
    public void setErrorQueue(QueueData errorQueue) {
        this.errorQueue = errorQueue;
    }

    public String toString() {
        return "ErrorExchangeConfig{type = " + type + ", exchangeName = " + exchangeName + ", errorQueue = " + errorQueue + "}";
    }
}
