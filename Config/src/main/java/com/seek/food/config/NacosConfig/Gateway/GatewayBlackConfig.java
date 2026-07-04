package com.seek.food.config.NacosConfig.Gateway;

import com.seek.food.config.Data.GatewayIpIdBlackData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Gateway_Black_Config)
public class GatewayBlackConfig {
    private GatewayIpIdBlackData ip;
    private GatewayIpIdBlackData id;


    public GatewayBlackConfig() {
    }

    public GatewayBlackConfig(GatewayIpIdBlackData ip, GatewayIpIdBlackData id) {
        this.ip = ip;
        this.id = id;
    }

    /**
     * 获取
     * @return ip
     */
    public GatewayIpIdBlackData getIp() {
        return ip;
    }

    /**
     * 设置
     * @param ip
     */
    public void setIp(GatewayIpIdBlackData ip) {
        this.ip = ip;
    }

    /**
     * 获取
     * @return id
     */
    public GatewayIpIdBlackData getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(GatewayIpIdBlackData id) {
        this.id = id;
    }

    public String toString() {
        return "GatewayBlackConfig{ip = " + ip + ", id = " + id + "}";
    }
}
