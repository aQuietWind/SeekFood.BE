package com.seek.food.util.Redis;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.TimeUtil.DurationUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class RedisUtil {
    public static final String cooldownValue="true";
    public static DefaultRedisScript<Boolean> luaQuickInit(String path){
        //初始化脚本对象
        DefaultRedisScript<Boolean> luaScript= new DefaultRedisScript<>();
        luaScript.setLocation(new ClassPathResource(path));  //设置Lua脚本地址，一般放于resources/Lua下
        luaScript.setResultType(Boolean.class);      //设置脚本返回值，与泛型保持一致
        return luaScript;
    };
    //用于满足lua脚本的集合化key操作
    public static List<String> toCollect(String ... items){
        return Arrays.asList(items);
    }

    //快速鉴别是否处于冷却期
    public static void checkCooldown(StringRedisTemplate stringRedisTemplate,String key,long duration){
        if (Boolean.FALSE.equals(stringRedisTemplate.opsForValue().setIfAbsent(key, cooldownValue, DurationUtil.getSecondDuration(duration)))) throw new BizException(ErrorCodeEnum.REQUEST_IN_COOLDOWN);
    }

    //快速从Stream消费者组获取消息
    public static List<MapRecord<String,Object,Object>> readStreamLastest(StringRedisTemplate stringRedisTemplate,String streamName,String consumerGroupName,int consumerId
            ,int count,int waitSeconds){
        return stringRedisTemplate.opsForStream().read(
                Consumer.from(consumerGroupName,"consumer"+consumerId),     //指定消费者
                StreamReadOptions.empty().count(count).block(Duration.ofSeconds(waitSeconds)),    //指定最大数与阻塞时间
                StreamOffset.create(streamName, ReadOffset.lastConsumed())      //指定目标stream与目标索引为最后一条未处理
        );      //返回值是一个定义好的MapRecord的List形式
    }

    //创建消费者组
    public static void createStreamConsumerGroup(StringRedisTemplate stringRedisTemplate,String queueName,String consumerGroupName){
        //可能已经存在生产者组
        try {
            stringRedisTemplate.opsForStream().createGroup(queueName,consumerGroupName);
        }catch (Exception ignored){}
    }
}
