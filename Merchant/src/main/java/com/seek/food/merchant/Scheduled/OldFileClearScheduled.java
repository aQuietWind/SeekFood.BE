package com.seek.food.merchant.Scheduled;

import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisStreamConfig;
import com.seek.food.config.NacosConfig.User.UserRedisStreamConfig;
import com.seek.food.util.FileUtil.FileRemove;
import com.seek.food.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
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
    public OldFileClearScheduled(StringRedisTemplate stringRedisTemplate, MerchantRedisStreamConfig merchantRedisStreamConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.oldFileStream = merchantRedisStreamConfig.getOldFileStream();
    }
    @Scheduled(fixedDelay = 5000)
    public void clearOldFiles() {
        for (;true;) {
            List<MapRecord<String,Object,Object>> records=RedisUtil.readStreamLastest(stringRedisTemplate,oldFileStream.getName(),
                    oldFileStream.getConsumer().getGroupName(),1,3,1);
            if (records.isEmpty()) break;
            //以下为业务逻辑
            for (MapRecord<String,Object,Object> record : records) {
                String path=(String)record.getValue().get(oldFileStream.getKeyName());
                try {
                    //获取数据并进行处理
                    FileRemove.removeFileByPath(path);
                }catch (Exception e){
                    log.error("有旧文件:{}在定时重新删除时发生错误:",path,e);
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
