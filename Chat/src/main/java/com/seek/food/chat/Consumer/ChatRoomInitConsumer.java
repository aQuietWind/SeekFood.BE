package com.seek.food.chat.Consumer;

import com.seek.food.chat.Mapper.ChatRoomMapper;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Chat.ChatRedisKeyConfig;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.dto.Chat.ChatRoomDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatRoomInitConsumer {
    private final ChatRoomMapper chatRoomMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ChatRedisKeyConfig chatRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;

    @Autowired
    public ChatRoomInitConsumer(ChatRoomMapper chatRoomMapper, StringRedisTemplate stringRedisTemplate, ChatRedisKeyConfig chatRedisKeyConfig, CommonParamRulesConfig commonParamRulesConfig) {
        this.chatRoomMapper = chatRoomMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.chatRedisKeyConfig = chatRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
    }
    @PostConstruct
    public void init() {
        stringRedisTemplate.opsForValue().setIfAbsent(chatRedisKeyConfig.getChatRoomIdCount().getName(),""+commonParamRulesConfig.getIdCapacity());
    }

    @RabbitListener(queues = MQNameKeyEnum.Order_Exchange_Chat_Room_Init_Queue)
    public void chatRoomInitQueue(ChatRoomDTO room) {

    }
















}
