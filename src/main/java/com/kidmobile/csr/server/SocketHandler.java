package com.kidmobile.csr.server;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private static final Logger log  =  Logger.getLogger(SocketHandler.class);
   
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished : id = " + session.getId());
        SocketSessionController.add(session);
        super.afterConnectionEstablished(session);
    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("afterConnectionClosed : id = " + session.getId() + ", status = " + status);
        SocketSessionController.remove(session);
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("handleTextMessage : id = " + session.getId());
        log.debug("msg : " + message.toString());
        log.debug("getPayload : " + message.getPayload());
        log.debug("getPayloadLength : " + message.getPayloadLength());
        log.debug("asBytes to String : " + new String(message.asBytes()));
        
        //if( message.getPayloadLength() < 3)
        //parseCmd()
        SocketSessionController.sendAllForWebsocket(message.getPayload());
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.debug("handleTransportError : id = "+ session.getId() + ", exception = " + exception);
    }
 
}
