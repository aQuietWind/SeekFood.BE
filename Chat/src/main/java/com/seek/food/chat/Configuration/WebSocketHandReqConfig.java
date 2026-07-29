package com.seek.food.chat.Configuration;

import com.seek.food.chat.Enum.WebSocketRequestParamsEnum;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

// 不用加 @Configuration / @Component
public class WebSocketHandReqConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //把握手请求的参数存入属性
        sec.getUserProperties().put(WebSocketRequestParamsEnum.Room_Id, request.getParameterMap().get(WebSocketRequestParamsEnum.Room_Id).getFirst());
    }
}
