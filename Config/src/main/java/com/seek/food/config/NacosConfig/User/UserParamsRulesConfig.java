package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Params_Rules_Config)
public class UserParamsRulesConfig {
    private int userIdStart;
    private long userIdCapacity;
    private int usernameLengthMax;
    private String phoneNumberRegex;
    private String passwordRegex;
    private HashSet<Integer> sexValues;

    public UserParamsRulesConfig() {
    }

    public UserParamsRulesConfig(int userIdStart, long userIdCapacity, int usernameLengthMax, String phoneNumberRegex, String passwordRegex, HashSet<Integer> sexValues) {
        this.userIdStart = userIdStart;
        this.userIdCapacity = userIdCapacity;
        this.usernameLengthMax = usernameLengthMax;
        this.phoneNumberRegex = phoneNumberRegex;
        this.passwordRegex = passwordRegex;
        this.sexValues = sexValues;
    }

    //------------------------
    //校验参数
    public boolean userIdCheck(long userId) {
        return (userId/userIdCapacity)==userIdStart;
    };
    public boolean usernameCheck(String username) {
        return (username.length()<usernameLengthMax&& !username.isEmpty());
    };
    public boolean passwordCheck(String password) {
        return password.matches(passwordRegex);
    };
    public boolean phoneNumberCheck(String phoneNumber) {
        return phoneNumber.matches(phoneNumberRegex);
    }
    public boolean sexCheck(Integer sex){
        return (sex==null||sexValues.contains(sex));
    }

    /**
     * 获取
     * @return userIdStart
     */
    public int getUserIdStart() {
        return userIdStart;
    }

    /**
     * 设置
     * @param userIdStart
     */
    public void setUserIdStart(int userIdStart) {
        this.userIdStart = userIdStart;
    }

    /**
     * 获取
     * @return userIdCapacity
     */
    public long getUserIdCapacity() {
        return userIdCapacity;
    }

    /**
     * 设置
     * @param userIdCapacity
     */
    public void setUserIdCapacity(long userIdCapacity) {
        this.userIdCapacity = userIdCapacity;
    }

    /**
     * 获取
     * @return usernameLengthMax
     */
    public int getUsernameLengthMax() {
        return usernameLengthMax;
    }

    /**
     * 设置
     * @param usernameLengthMax
     */
    public void setUsernameLengthMax(int usernameLengthMax) {
        this.usernameLengthMax = usernameLengthMax;
    }

    /**
     * 获取
     * @return phoneNumberRegex
     */
    public String getPhoneNumberRegex() {
        return phoneNumberRegex;
    }

    /**
     * 设置
     * @param phoneNumberRegex
     */
    public void setPhoneNumberRegex(String phoneNumberRegex) {
        this.phoneNumberRegex = phoneNumberRegex;
    }

    /**
     * 获取
     * @return passwordRegex
     */
    public String getPasswordRegex() {
        return passwordRegex;
    }

    /**
     * 设置
     * @param passwordRegex
     */
    public void setPasswordRegex(String passwordRegex) {
        this.passwordRegex = passwordRegex;
    }

    /**
     * 获取
     * @return sexValues
     */
    public HashSet<Integer> getSexValues() {
        return sexValues;
    }

    /**
     * 设置
     * @param sexValues
     */
    public void setSexValues(HashSet<Integer> sexValues) {
        this.sexValues = sexValues;
    }

    public String toString() {
        return "UserParamsRulesConfig{userIdStart = " + userIdStart + ", userIdCapacity = " + userIdCapacity + ", usernameLengthMax = " + usernameLengthMax + ", phoneNumberRegex = " + phoneNumberRegex + ", passwordRegex = " + passwordRegex + ", sexValues = " + sexValues + "}";
    }

    //-----------------------------

}
