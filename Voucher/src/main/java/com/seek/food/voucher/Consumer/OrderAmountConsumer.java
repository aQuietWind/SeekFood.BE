package com.seek.food.voucher.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.user.Mapper.UserMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
public class OrderAmountConsumer {
    private UserMapper userMapper;
    @Autowired
    public OrderAmountConsumer(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Delete_File_Queue)
    public void updateFileQueue(long userId){
        userMapper.increaseOrderAmount(userId);
    }
}
