package com.seek.food.util.MQ;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class MQUtil {
    //产生CorrelationData
    public static CorrelationData getCorrelation(String exChangeName,String routingKey){
        //生成一个带有随机id的correlationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //设置回调函数
        correlationData.getFuture().whenComplete((r,e)->{
            if (e!=null)log.error("Exchange:{}发送消息到RoutingKey:{},发生异常",exChangeName,routingKey,e);
            if (!r.isAck())log.error("Exchange:{}发送消息到RoutingKey:{},未能成功到达交换机",exChangeName,routingKey);
        });
        return correlationData;
    }
    //发送消息
    public static void send(String exchangeName, String routingKey, Object message, RabbitTemplate rabbitTemplate){
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message,getCorrelation(exchangeName,routingKey));
    }

    //生成一个仲裁队列
    public static Queue generateQuorumQueue(String queueName){
        Map<String, Object> args = new HashMap<>();
        // 设置队列模式为quorum仲裁模式
        args.put("x-queue-type", "quorum");
        return new Queue(queueName, true, false, false, args);
    }


}
