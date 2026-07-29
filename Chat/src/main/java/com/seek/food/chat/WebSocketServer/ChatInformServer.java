package com.seek.food.chat.WebSocketServer;

import com.seek.food.chat.Configuration.WebSocketHandReqConfig;
import com.seek.food.chat.Enum.RequestPathEnum;
import com.seek.food.chat.Enum.WebSocketRequestParamsEnum;
import com.seek.food.chat.Service.ChatRoomService;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.util.Context.TokenIdContext;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
//设置为自定义的Endpoint配置
@ServerEndpoint(value = RequestPathEnum.Chat_Room_WebSocket,configurator = WebSocketHandReqConfig.class)
@Slf4j
public class ChatInformServer {

    // 存放所有在线会话
    private static final ConcurrentHashMap<Long,Session> Session_Map = new ConcurrentHashMap<>();
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final ChatRoomService chatRoomService;

    public ChatInformServer(CommonParamRulesConfig commonParamRulesConfig, ChatRoomService chatRoomService) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.chatRoomService = chatRoomService;
    }

    // 连接建立成功
    @OnOpen
    public void onOpen(Session session , EndpointConfig config) {
        Long roomId=null;
        try {
            roomId= (Long) session.getUserProperties().get(WebSocketRequestParamsEnum.Room_Id);
        }catch (Exception e){return;}
        //检查该请求
        commonParamRulesConfig.commonIdCheck(roomId);
        long tokenId=
        chatRoomService.checkIdAndRoom(roomId, TokenIdContext.getAndToLong());
        Session_Map.put(roomId,session);
    }

    // 收到客户端消息
    @OnMessage
    public void onMessage(String msg, Session session) throws IOException {
        System.out.println("onMessage: " + msg);
        // 单发回复
        session.getBasicRemote().sendText("服务端已收到：" + msg);
        // 全员广播
        broadcast(msg);
    }

    // 连接关闭
    @OnClose
    public void onClose(Session session) {
        SESSION_SET.remove((Long) session.getUserProperties().get(WebSocketRequestParamsEnum.Room_Id));
    }

    // 异常回调
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    // 广播消息
    private void broadcast(String msg) throws IOException {
        for (Session s : SESSION_SET) {
            if (s.isOpen()) {
                s.getBasicRemote().sendText(msg);
            }
        }
    }
}
