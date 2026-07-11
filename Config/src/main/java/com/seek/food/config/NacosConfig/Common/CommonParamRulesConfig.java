package com.seek.food.config.NacosConfig.Common;

import com.seek.food.config.Enum.ConfigKeyEnum;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.time.LocalDate;
import java.util.HashSet;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Common_Param_Rules_Key)
@Data
public class CommonParamRulesConfig {
    private int userIdStart;
    private int merchantIdStart;
    private int riderIdStart;
    private int adminIdStart;
    private int personNameMax;
    private long idCapacity;
    private String phoneNumberRegex;
    private String passwordRegex;
    private String codeRegex;
    private HashSet<Integer> sexValues;
    private HashSet<String> imageType;
    private long imageSize;
    //------------------------
    //校验参数
    public void userIdCheck(long userId) {
        if (!((userId/idCapacity)==userIdStart) ) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void merchantIdCheck(long merchant) {
        if (! ((merchant/idCapacity)==merchantIdStart) ) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void riderIdCheck(long riderId) {
        if (! ((riderId/idCapacity)==riderIdStart) ) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void adminIdCheck(long adminId) {
        if (! ((adminId/idCapacity)==adminIdStart) ) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void passwordCheck(String password) {
        if (password == null || !password.matches(passwordRegex)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void codeCheck(String code) {
        if (code==null||!code.matches(codeRegex)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void phoneNumberCheck(String phoneNumber) {
        if (phoneNumber==null||!phoneNumber.matches(phoneNumberRegex)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void sexCheck(Integer sex){
        if (sex!=null&&!sexValues.contains(sex)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void birthdayCheck(LocalDate birthday){
        if(birthday!=null&&LocalDate.now().isBefore(birthday)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void personNameCheck(String personName) {
        if(personName==null||personName.length()>personNameMax) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }


}
