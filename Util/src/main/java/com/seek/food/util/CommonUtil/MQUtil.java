package com.seek.food.util.CommonUtil;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.UUID;

public class MQUtil {
    //发送消息至MQ
    public static CorrelationData getCorrelation(String mqName,String exChangeName, Logger logger){
        //生成一个带有随机id的correlationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //设置回调函数
        correlationData.getFuture().whenComplete((r,e)->{
            if (e!=null)logger.error("交换机{}发送消息到{}的过程中间发生异常",exChangeName,mqName,e);
            if (!r.isAck())logger.error("交换机{}发送消息到{}的过程中未能成功到达交换机",exChangeName,mqName);
        });
        return correlationData;
    }
}
