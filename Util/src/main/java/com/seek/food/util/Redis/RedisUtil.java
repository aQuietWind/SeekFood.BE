package com.seek.food.util.Redis;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.Function.RunFunction;
import com.seek.food.util.Function.RunWithParam;
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

    //快速进行Stream获取消息并且消费,直至无法获取到消息
    public static void readStreamAndHandle(StringRedisTemplate stringRedisTemplate, String streamName, String consumerGroupName, int consumerId
            , int count, int waitSeconds,String streamKeyName, RunWithParam<Object> handler){
        for (;true;) {
            List<MapRecord<String, Object, Object>> records = RedisUtil.readStreamLastest(stringRedisTemplate, streamName
                    , consumerGroupName, consumerId, count, waitSeconds);
            if (records.isEmpty()) break;
            //以下为业务逻辑
            for (MapRecord<String, Object, Object> record : records) {
                handler.function(record.getValue().get(streamKeyName));
                //通过id确认消息
                stringRedisTemplate.opsForStream().acknowledge(streamName,consumerGroupName,record.getId());
            }
        }
    }

    //创建消费者组
    public static void createStreamConsumerGroup(StringRedisTemplate stringRedisTemplate,String queueName,String consumerGroupName){
        //可能已经存在生产者组
        try {
            stringRedisTemplate.opsForStream().createGroup(queueName,consumerGroupName);
        }catch (Exception ignored){}
    }

    //key拼接
    public static String redisKeyMix(String start,Object end){
        return start+end;
    }

    //全局BitMap设置值
    public static boolean oftenSetBit(StringRedisTemplate stringRedisTemplate,String redisKeyName,long id
            ,boolean value,long idCapacity){
        //注意返回值是原来的位置状态
        Boolean originBit=stringRedisTemplate.opsForValue().setBit(redisKeyName, (id % idCapacity), value);
        if (originBit==null)return false;
        return value!=originBit;
    }

    //个体BitMap设置值
    public static boolean setBit(StringRedisTemplate stringRedisTemplate,String redisKeyName,boolean value){
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setBit(redisKeyName, 0, value));
    }

    //全局BitMap获取值
    public static boolean oftenGetBit(StringRedisTemplate stringRedisTemplate,String redisKeyName,long id,long idCapacity){
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(redisKeyName, (id % idCapacity)));
    }

    //个体BitMap获取值
    public static boolean getBit(StringRedisTemplate stringRedisTemplate,String redisKeyName){
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(redisKeyName, 0));
    }

    //设置BitMap值成功后执行函数
    public static void oftenSetBitAndAct(StringRedisTemplate stringRedisTemplate, String redisKeyName, long id
            , boolean value, long idCapacity, RunFunction runFunction){
        boolean result=oftenSetBit(stringRedisTemplate,redisKeyName,id,value,idCapacity);
        System.err.println(result);
        if (result) runFunction.function();
    }













}
