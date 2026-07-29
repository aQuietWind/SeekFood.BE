package com.seek.food.config.NacosConfig.Chat;

import com.seek.food.config.Enum.ConfigKeyEnum;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.time.LocalDateTime;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Chat_Params_Rules_Config)
public class ChatParamsRulesConfig {
    private int chatDescriptionMax;
    private int chatRoomEndingDays;
    private int chatRecordWithdrawDeadlineMinutes;
    private String chatRecordImageDest;

    public void chatDescriptionCheck(String description) {
        if (description!=null&&(description.isBlank()||description.length()>chatDescriptionMax) )throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
