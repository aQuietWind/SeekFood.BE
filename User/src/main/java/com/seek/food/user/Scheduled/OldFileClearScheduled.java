package com.seek.food.user.Scheduled;

import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.NacosConfig.User.UserRedisStreamConfig;
import com.seek.food.util.FileUtil.FileRemove;
import com.seek.food.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//重新对无法删除的文件进行删除
@Component
@Slf4j
public class OldFileClearScheduled {
    private StringRedisTemplate stringRedisTemplate;
    private RedisStreamData oldFileStream;
    @Autowired
    public OldFileClearScheduled(StringRedisTemplate stringRedisTemplate,UserRedisStreamConfig userRedisStreamConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        System.err.println(userRedisStreamConfig);
        this.oldFileStream = userRedisStreamConfig.getOldFileStream();
    }
    @Scheduled(fixedDelay = 5000)
    public void clearOldFiles() {
        for (;true;) {
            List<MapRecord<String,Object,Object>> records=RedisUtil.readStreamLastest(stringRedisTemplate,oldFileStream.getName(),
                    oldFileStream.getConsumer().getGroupName(),1,3,3);
            if (records.isEmpty()) break;
            //以下为业务逻辑
            for (MapRecord<String,Object,Object> record : records) {
                try {
                    //获取数据并进行处理
                    FileRemove.removeFileByPath((String) record.getValue().get(oldFileStream.getKeyName()));
                }catch (Exception e){
                    log.error("有旧文件在定时重新删除时发生错误，需查看排错");
                }
                //通过id确认消息
                stringRedisTemplate.opsForStream().acknowledge(oldFileStream.getName(),oldFileStream.getConsumer().getGroupName(),record.getId());
            }
        }
    }
    @PostConstruct
    public void init(){
        RedisUtil.createStreamConsumerGroup(stringRedisTemplate,oldFileStream.getName(),oldFileStream.getConsumer().getGroupName());
    }










}
