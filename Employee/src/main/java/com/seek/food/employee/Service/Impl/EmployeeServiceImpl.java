package com.seek.food.employee.Service.Impl;

import com.seek.food.config.NacosConfig.Employee.EmployeeParamsRulesConfig;
import com.seek.food.config.NacosConfig.Employee.EmployeeRedisKeyConfig;
import com.seek.food.config.NacosConfig.MQ.EmployeeExchangeConfig;
import com.seek.food.dto.Employee.EmployeeDTO;
import com.seek.food.employee.Caffeine.EmployeeCaffeine;
import com.seek.food.employee.Mapper.EmployeeMapper;
import com.seek.food.employee.Service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeExchangeConfig employeeExchangeConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final EmployeeParamsRulesConfig employeeParamsRulesConfig;
    private final EmployeeCaffeine employeeCaffeine;
    private final EmployeeRedisKeyConfig employeeRedisKeyConfig;
    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper,EmployeeExchangeConfig employeeExchangeConfig
    , StringRedisTemplate stringRedisTemplate, EmployeeParamsRulesConfig employeeParamsRulesConfig, EmployeeCaffeine employeeCaffeine
    , EmployeeRedisKeyConfig employeeRedisKeyConfig) {
        this.employeeMapper = employeeMapper;
        this.employeeExchangeConfig = employeeExchangeConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.employeeParamsRulesConfig = employeeParamsRulesConfig;
        this.employeeCaffeine = employeeCaffeine;
        this.employeeRedisKeyConfig = employeeRedisKeyConfig;
    }

    @Override
    public void insertEmployee(String employeeName){
        
    }

    @Override
    public List<EmployeeDTO> getSimpleEmployee(int start, int need){

    }

    @Override
    public List<EmployeeDTO> getSimpleByResign(boolean resign,int start,int need){

    }

    @Override
    public List<EmployeeDTO> searchSimpleByName(String employeeName,int start,int need){

    }

    @Override
    public List<EmployeeDTO> searchSimpleByDep(String depName,int start,int need){

    }

    @Override
    public EmployeeDTO getDetailEmployee(long employeeId){

    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO){

    }

    @Override
    public void resignEmployee(long employeeId){

    }

    @Override
    public void deleteEmployee(long employeeId){

    }
}
