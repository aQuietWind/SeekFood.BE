package com.seek.food.config.NacosConfig.Gateway;


import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;

@RefreshScope
@ConfigurationProperties(prefix = ConfigKeyEnum.Gateway_Request_Path_Config)
public class GatewayRequestPathConfig {
    private HashSet<String> allowPath;
    private HashSet<String> rejectPath;

    public GatewayRequestPathConfig() {
    }

    public GatewayRequestPathConfig(HashSet<String> allowPath, HashSet<String> rejectPath) {
        this.allowPath = allowPath;
        this.rejectPath = rejectPath;
    }

    public boolean checkAllowPath(String path){
        return allowPath.contains(path);
    }
    public boolean checkRejectPath(String path){
        return rejectPath.contains(path);
    }

    /**
     * 获取
     * @return allowPath
     */
    public HashSet<String> getAllowPath() {
        return allowPath;
    }

    /**
     * 设置
     * @param allowPath
     */
    public void setAllowPath(HashSet<String> allowPath) {
        this.allowPath = allowPath;
    }

    /**
     * 获取
     * @return rejectPath
     */
    public HashSet<String> getRejectPath() {
        return rejectPath;
    }

    /**
     * 设置
     * @param rejectPath
     */
    public void setRejectPath(HashSet<String> rejectPath) {
        this.rejectPath = rejectPath;
    }

    public String toString() {
        return "GatewayRequestPathConfig{allowPath = " + allowPath + ", rejectPath = " + rejectPath + "}";
    }
}






