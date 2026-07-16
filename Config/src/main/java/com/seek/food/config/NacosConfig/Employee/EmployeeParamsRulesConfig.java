package com.seek.food.config.NacosConfig.Employee;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Employee_Params_Rules_Config)
public class EmployeeParamsRulesConfig {
}
