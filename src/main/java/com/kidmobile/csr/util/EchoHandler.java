package com.kidmobile.csr.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoHandler extends TextWebSocketHandler {
    private static final Logger log  =  Logger.getLogger(EchoHandler.class);
    List<WebSocketSession> lists = new ArrayList<WebSocketSession>();
   
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished : " + session.getUri());
        log.debug(session.getId() + " + connected");
        //lists.add(session);
        SessionMapper.add(session);
        super.afterConnectionEstablished(session);
    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //log.debug("afterConnectionClosed : {}, {}", session, status);
        SessionMapper.remove(session);
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //log.debug("handleTextMessage : {}, {}", session, message.getPayload());
        SessionMapper.sendAllForWebsocket(message.getPayload());
    	//TextMessage echoMessage = new TextMessage("ECHO :" + message.getPayload());
    	//for(WebSocketSession s : lists)
    	//	s.sendMessage(echoMessage);
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //log.debug("handleTransportError : {} {}", session, exception);
    }
 
}
