package com.seek.food.user.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.food.dto.User.OldFileDTO;
import com.seek.food.user.Mapper.OldFileMapper;
import com.seek.food.util.FileUtil.FileRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateFileConsumer {
    private OldFileMapper oldFileMapper;
    private UserParamsRulesConfig userParamsRulesConfig;
    @Autowired
    public UpdateFileConsumer(OldFileMapper oldFileMapper,UserParamsRulesConfig userParamsRulesConfig) {
        this.oldFileMapper = oldFileMapper;
        this.userParamsRulesConfig = userParamsRulesConfig;
    }

    @RabbitListener(queues = MQNameKeyEnum.User_Exchange_Update_File_Queue)
    public void updateFileQueue(long userId){
        String addr = oldFileMapper.getOldFileByUserId(userId).getFileAddr();
        try {
            FileRemove.removeFile(userParamsRulesConfig.getHeaderImagePath(), addr);
            oldFileMapper.deleteFile(addr);
        }catch (Exception e){
            //不再进行重试，而是留给spring后台线程进行定时处理
            log.error("addr:{},在删除时发生错误",addr,e);
        }
    }













}
