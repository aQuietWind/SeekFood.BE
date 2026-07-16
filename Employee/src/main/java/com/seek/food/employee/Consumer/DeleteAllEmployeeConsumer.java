package com.seek.food.employee.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.employee.Mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DeleteAllEmployeeConsumer {
    private final StringRedisTemplate stringRedisTemplate;
    private final EmployeeMapper employeeMapper;
    @Autowired
    public DeleteAllEmployeeConsumer(StringRedisTemplate stringRedisTemplate, EmployeeMapper employeeMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.employeeMapper = employeeMapper;
    }


    @RabbitListener(queues = MQNameKeyEnum.Employee_Exchange_Change_Amount_Employee_Queue)
    public void deleteAllEmployeeQueue(long merchantId){

    }















}
