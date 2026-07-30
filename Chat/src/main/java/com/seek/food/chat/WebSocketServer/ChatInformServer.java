package com.seek.food.chat.WebSocketServer;

import com.seek.food.chat.Configuration.WebSocketHandReqConfig;
import com.seek.food.chat.Enum.RequestPathEnum;
import com.seek.food.chat.Enum.WebSocketRequestParamsEnum;
import com.seek.food.chat.Service.ChatRoomService;
import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Chat.ChatRedisKeyConfig;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Redis.RedisUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
//设置为自定义的Endpoint配置
@ServerEndpoint(value = RequestPathEnum.Chat_Room_WebSocket,configurator = WebSocketHandReqConfig.class)
@Slf4j
public class ChatInformServer {

    // 存放所有在线会话
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Session>> Session_Map = new ConcurrentHashMap<>();
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final ChatRoomService chatRoomService;
    private final StringRedisTemplate stringRedisTemplate;
    private final ChatRedisKeyConfig chatRedisKeyConfig;

    public ChatInformServer(CommonParamRulesConfig commonParamRulesConfig, ChatRoomService chatRoomService, StringRedisTemplate stringRedisTemplate, ChatRedisKeyConfig chatRedisKeyConfig) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.chatRoomService = chatRoomService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.chatRedisKeyConfig = chatRedisKeyConfig;
    }

    // 连接建立成功
    @OnOpen
    public void onOpen(Session session) {
        Long roomId=null;
        try {
            roomId= (Long) session.getUserProperties().get(WebSocketRequestParamsEnum.Room_Id);
        }catch (Exception e){return;}
        //检查该请求参数
        commonParamRulesConfig.commonIdCheck(roomId);
        //获取该请求账户的Id
        long tokenId=quickGetIdAndCheckCooldown(chatRedisKeyConfig.getChatRoomWebsocketCooldown(),TokenIdContext.getAndToLong());
        //检查该账户是否有权限监听该合格聊天室
        chatRoomService.checkIdAndRoom(roomId, tokenId);
        //放置该session
        Session_Map.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<Session>());
        Session_Map.get(roomId).add(session);
    }

    // 收到客户端消息
    @OnMessage
    public void onMessage(String msg, Session session){
        //禁止客户端消息回话,故不做如何操作
    }

    // 连接关闭
    @OnClose
    public void onClose(Session session) {
        //删除该会话
        Session_Map.get((Long) session.getUserProperties().get(WebSocketRequestParamsEnum.Room_Id)).remove(session);
    }

    // 异常回调
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    //对指定的聊天室id广播消息
    public void broadcastRoomId(long roomId,String msg) throws IOException {
        CopyOnWriteArrayList<Session> list=Session_Map.get(roomId);
        for (Session session : list) {
            //发送消息
            if (session.isOpen()) session.getBasicRemote().sendText(msg);
        }
    }

    private void quickCooldown(RedisKeyData key, Object id){
        RedisUtil.checkCooldown(stringRedisTemplate,key.getRedisKey(id),key.getDuration());
    }
    private long quickGetIdAndCheckCooldown(RedisKeyData key, long id){
        quickCooldown(key,id);
        return id;
    }
}
