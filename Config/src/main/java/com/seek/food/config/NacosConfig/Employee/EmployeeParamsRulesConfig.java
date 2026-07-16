package com.seek.food.config.NacosConfig.Employee;

import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Employee_Params_Rules_Config)
@Data
public class EmployeeParamsRulesConfig {
    private int employeeNameMax;
    private int employeePositionMax;
    private int employeeDepNameMax;
    private int employeeAddrMax;
    private int employeeDescriptionMax;
    private String personImageDest;
}
