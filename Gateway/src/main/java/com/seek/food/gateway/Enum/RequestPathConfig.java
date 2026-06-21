package com.seek.food.gateway.Enum;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@RefreshScope
@ConfigurationProperties(prefix = "request.path")
@Component
public class RequestPathConfig {
    private List<String> allowPath;
    private List<String> rejectPath;

    public boolean checkAllowPath(String path){
        for (int i = 0; i < allowPath.size(); i++) {
            if (allowPath.get(i).equals(path)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkRejectPath(String path){
        for (int i = 0; i < rejectPath.size(); i++) {
            if (rejectPath.get(i).equals(path)) {
                return true;
            }
        }
        return false;
    }


    public RequestPathConfig() {
    }

    public RequestPathConfig(List<String> allowPath, List<String> rejectPath) {
        this.allowPath = allowPath;
        this.rejectPath = rejectPath;
    }

    /**
     * 获取
     * @return allowPath
     */
    public List<String> getAllowPath() {
        return allowPath;
    }

    /**
     * 设置
     * @param allowPath
     */
    public void setAllowPath(List<String> allowPath) {
        this.allowPath = allowPath;
    }

    /**
     * 获取
     * @return rejectPath
     */
    public List<String> getRejectPath() {
        return rejectPath;
    }

    /**
     * 设置
     * @param rejectPath
     */
    public void setRejectPath(List<String> rejectPath) {
        this.rejectPath = rejectPath;
    }

    public String toString() {
        return "RequestPathEnum{allowPath = " + allowPath + ", rejectPath = " + rejectPath + "}";
    }
}






