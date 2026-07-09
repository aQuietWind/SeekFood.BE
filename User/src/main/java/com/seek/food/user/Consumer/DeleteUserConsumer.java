package com.seek.food.user.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteUserConsumer {
    @Autowired
    public DeleteUserConsumer() {}

    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Delete_User_Queue)
    public void deleteUserQueue(long userId){
        System.err.println(userId);
    }

}
