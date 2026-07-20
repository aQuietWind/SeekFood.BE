package com.seek.food.config.NacosConfig.Fund;

import com.seek.food.config.Enum.ConfigKeyEnum;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Fund_Params_Rules_Config)
@Data
public class FundParamsRulesConfig {
    private int fundRechargeAmountMax;
    private int fundWithdrawAmountMax;


    public void fundRechargeAmountCheck(int fundRechargeAmount) {
        if (fundRechargeAmount > fundRechargeAmountMax||fundRechargeAmount <= 0) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
    public void fundWithdrawAmountCheck(int fundWithdrawAmount) {
        if (fundWithdrawAmount > fundWithdrawAmountMax||fundWithdrawAmount <= 0) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
