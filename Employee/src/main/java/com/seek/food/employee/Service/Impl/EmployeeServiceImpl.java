package com.seek.food.employee.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Employee.EmployeeParamsRulesConfig;
import com.seek.food.config.NacosConfig.Employee.EmployeeRedisKeyConfig;
import com.seek.food.config.NacosConfig.MQ.EmployeeExchangeConfig;
import com.seek.food.dto.Common.ChangeAmountDTO;
import com.seek.food.dto.Employee.EmployeeDTO;
import com.seek.food.employee.Caffeine.EmployeeCaffeine;
import com.seek.food.employee.Mapper.EmployeeMapper;
import com.seek.food.employee.Service.EmployeeService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper,EmployeeExchangeConfig employeeExchangeConfig
    , StringRedisTemplate stringRedisTemplate, EmployeeParamsRulesConfig employeeParamsRulesConfig, EmployeeCaffeine employeeCaffeine
    , EmployeeRedisKeyConfig employeeRedisKeyConfig, CommonParamRulesConfig commonParamRulesConfig, RabbitTemplate rabbitTemplate) {
        this.employeeMapper = employeeMapper;
        this.employeeExchangeConfig = employeeExchangeConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.employeeParamsRulesConfig = employeeParamsRulesConfig;
        this.employeeCaffeine = employeeCaffeine;
        this.employeeRedisKeyConfig = employeeRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.rabbitTemplate = rabbitTemplate;
        stringRedisTemplate.opsForValue().setIfAbsent(employeeRedisKeyConfig.getEmployeeIdCount().getName(),"");
    }

    @Override
    public void insertEmployee(String employeeName){
        //检查名字格式
        commonParamRulesConfig.personNameCheck(employeeName);
        //获取商家Id
        long merchantId= quickGetMerchantId();
        //检查冷却期
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeInsertCooldown(),merchantId);
        //新增
        employeeMapper.insertEmployee(employeeName
                , IdUtil.IdGenerateByIncrease(employeeRedisKeyConfig.getEmployeeIdCount().getName(),stringRedisTemplate),merchantId);
        //MQ转发
        MQUtil.send(employeeExchangeConfig.getExchangeName(),employeeExchangeConfig.getChangeEmployeeAmountQueue().getRoutingKey()
        , new ChangeAmountDTO(merchantId,1),rabbitTemplate);
    }

    @Override
    public List<EmployeeDTO> getSimpleEmployee(int start, int need){
        //检查格式
        commonParamRulesConfig.needNumberCheck(need);
        //获取商家Id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeGetSimpleCooldown(),merchantId);
        return employeeMapper.getSimpleEmployee(start,need,merchantId);
    }

    @Override
    public List<EmployeeDTO> getSimpleByResign(boolean resign,int start,int need){
        //检查格式
        commonParamRulesConfig.needNumberCheck(need);
        //获取商家Id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeGetSimpleCooldown(),merchantId);
        return employeeMapper.getSimpleByResign(resign,start,need,merchantId);
    }

    @Override
    public List<EmployeeDTO> searchSimpleByName(String employeeName,int start,int need){
        //检查格式
        commonParamRulesConfig.needNumberCheck(need);
        commonParamRulesConfig.personNameCheck(employeeName);
        //获取商家id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeGetSimpleCooldown(),merchantId);
        return employeeMapper.getSimpleByName(employeeName,start,need,merchantId);
    }

    @Override
    public List<EmployeeDTO> searchSimpleByDep(String depName,int start,int need){
        //检查参数
        commonParamRulesConfig.needNumberCheck(need);
        employeeParamsRulesConfig.depNameCheck(depName);
        //获取商家Id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeGetSimpleCooldown(),merchantId);
        return employeeMapper.getSimpleByDep(depName,start,need,merchantId);
    }

    @Override
    public EmployeeDTO getDetailEmployee(long employeeId){
        //检查格式
        commonParamRulesConfig.commonIdCheck(employeeId);
        //获取商家id
        long merchantId=quickGetMerchantId();
        //缓存获取
        return employeeCaffeine.getAndAutoLoad(employeeId,stringRedisTemplate,employeeRedisKeyConfig.getEmployeeMessageCaffeine().getRedisKey(employeeId)
        ,employeeRedisKeyConfig.getEmployeeMessageCaffeine().getDuration(), EmployeeDTO.class
        ,key->employeeMapper.getDetailEmployee(employeeId,merchantId));
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO){
        //检查格式

    }

    @Override
    public void resignEmployee(long employeeId){
        //检查格式
        commonParamRulesConfig.commonIdCheck(employeeId);
        //获取商家id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeUpdateResignCooldown(),merchantId);
        //更改雇佣状态
        employeeMapper.resignEmployee(employeeId,merchantId);
    }

    @Override
    public void deleteEmployee(long employeeId){
        //检查格式
        commonParamRulesConfig.commonIdCheck(employeeId);
        //获取商家id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCheckCooldown(employeeRedisKeyConfig.getEmployeeDeleteCooldown(),merchantId);
        //删除职员
        employeeMapper.deleteEmployee(employeeId,merchantId);
        //发送MQ同步
        MQUtil.send(employeeExchangeConfig.getExchangeName(),employeeExchangeConfig.getChangeEmployeeAmountQueue().getRoutingKey()
                , new ChangeAmountDTO(merchantId,-1),rabbitTemplate);
    }

    private long quickGetMerchantId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private void quickCheckCooldown(RedisKeyData key,long id){
        RedisUtil.checkCooldown(stringRedisTemplate,key.getRedisKey(id),key.getDuration());
    }
}
