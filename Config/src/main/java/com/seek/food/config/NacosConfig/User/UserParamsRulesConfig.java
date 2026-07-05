package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Params_Rules_Config)
@Data
public class UserParamsRulesConfig {
    private int userIdStart;
    private long userIdCapacity;
    private int usernameLengthMax;
    private String phoneNumberRegex;
    private String passwordRegex;
    private HashSet<Integer> sexValues;
    private HashSet<String> headerImageType;
    private long headerImageSize;
    private String headerImagePath;

    //------------------------
    //校验参数
    public boolean userIdCheck(long userId) {
        return (userId/userIdCapacity)==userIdStart;
    };
    public boolean usernameCheck(String username) {
        return (username.length()<usernameLengthMax&& !username.isEmpty());
    };
    public boolean passwordCheck(String password) {
        return password!=null&&password.matches(passwordRegex);
    };
    public boolean phoneNumberCheck(String phoneNumber) {
        return phoneNumber!=null&&phoneNumber.matches(phoneNumberRegex);
    }
    public boolean sexCheck(Integer sex){
        return (sex==null||sexValues.contains(sex));
    }
}
