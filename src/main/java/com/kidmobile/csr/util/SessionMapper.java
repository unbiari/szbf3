package com.kidmobile.csr.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class SessionMapper {

    private static final ConcurrentHashMap<String, WebSocketSession> websocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
   
    public static synchronized void add(WebSocketSession session) {
        //log.debug("add Session : {} {]", session.getId(), session.toString());
        websocketSessionMap.put(session.getId(), session);
    }
    
    public static synchronized void remove(WebSocketSession session) {
        //log.debug("remove Session : {} {]", session.getId(), session.toString());
        websocketSessionMap.remove(session.getId());
    }
    
    public static void sendAllForWebsocket(String message) {
        //log.debug("sessionMap.size() : {}", sessionMap.size());
        for(Map.Entry<String, WebSocketSession> em : websocketSessionMap.entrySet()){
            try {
                WebSocketSession session = em.getValue();
                   if(session.isOpen()){
                       synchronized (session) {
                           session.sendMessage(new TextMessage(message));
                       }
                   }
            } catch (IOException e) {
                   //log.error("", e);
            }
        }
    }
}
