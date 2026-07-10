package com.seek.food.user.Consumer;

import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.config.NacosConfig.User.UserRedisStreamConfig;
import com.seek.food.util.FileUtil.FileRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UpdateFileConsumer {
    private UserParamsRulesConfig userParamsRulesConfig;
    private StringRedisTemplate stringRedisTemplate;
    private RedisStreamData oldFileStream;
    @Autowired
    public UpdateFileConsumer(UserParamsRulesConfig userParamsRulesConfig
    ,StringRedisTemplate stringRedisTemplate,UserRedisStreamConfig userRedisStreamConfig) {
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.oldFileStream = userRedisStreamConfig.getOldFileStream();
    }


    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Update_File_Queue)
    public void updateFileQueue(String addr){
        try {
            FileRemove.removeFile(userParamsRulesConfig.getHeaderImageDest(), addr);
        }catch (Exception e){
            //不再进行重试，而是留给spring后台线程进行定时处理
            log.error("addr:{},在删除时发生错误",addr,e);
            Map<String,Object> map = new HashMap<>();
            map.put(oldFileStream.getKeyName(),FileRemove.resolvePath(userParamsRulesConfig.getHeaderImageDest(),addr));
            stringRedisTemplate.opsForStream().add(oldFileStream.getName(),map);
        }
    }












}
