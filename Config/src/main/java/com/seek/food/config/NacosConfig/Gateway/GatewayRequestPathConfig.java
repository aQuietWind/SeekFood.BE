package com.seek.food.config.NacosConfig.Gateway;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;

@RefreshScope
@ConfigurationProperties(prefix = "gateway.self.request.path")
public class GatewayRequestPathConfig {
    private HashSet<String> allowPath;
    private HashSet<String> rejectPath;

    public boolean checkAllowPath(String path){
        return allowPath.contains(path);
    }
    public boolean checkRejectPath(String path){
        return rejectPath.contains(path);
    }


    public GatewayRequestPathConfig() {
    }

    public GatewayRequestPathConfig(HashSet<String> allowPath, HashSet<String> rejectPath) {
        this.allowPath = allowPath;
        this.rejectPath = rejectPath;
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
        return "RequestPathConfig{allowPath = " + allowPath + ", rejectPath = " + rejectPath + "}";
    }
}






