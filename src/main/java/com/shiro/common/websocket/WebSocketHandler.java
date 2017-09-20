package com.shiro.common.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyiming on 2017/9/7.
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private Log log = LogFactory.getLog(this.getClass());
    private List<WebSocketSession> sessionList = new ArrayList<>();

    //接收消息文本
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(message.getPayload());
        TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
        session.sendMessage(returnMessage);
        super.handleTextMessage(session, message);
    }

    //建立连接后处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connect to the websocket success......");
        sessionList.add(session);
        super.afterConnectionEstablished(session);
    }

    //抛出异常时的处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        log.info("websocket connection error......");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("websocket connection closed......");
        super.afterConnectionClosed(session, status);
    }

    /**
     * 发送消息给所有在线用户
     * @param message
     */
    public void sendMessageToOnlineUsers(String message){
        for (WebSocketSession session: sessionList){
            TextMessage textMessage = new TextMessage(message);
            try{
                if (session.isOpen()){
                    session.sendMessage(textMessage);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
