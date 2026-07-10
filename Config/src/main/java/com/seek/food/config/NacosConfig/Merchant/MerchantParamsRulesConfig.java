package com.seek.food.config.NacosConfig.Merchant;

import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Merchant_Params_Rules_Config)
public class MerchantParamsRulesConfig {
    private int merchantNameMax;
    private int merchantMasterNameMax;
    private int proofImageNumberMax;
    private int showImageNumberMax;
    private int showDescriptionMax;
    private int merchantAddrMax;
    private String proofImageDest;
    private String showImageDest;
}
