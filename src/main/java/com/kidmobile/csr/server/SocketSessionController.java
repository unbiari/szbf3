package com.kidmobile.csr.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.kidmobile.csr.user_tbl.User_Tbl;
import com.kidmobile.csr.user_tbl.User_TblService;

@Controller
public class SocketSessionController {
	private static final Logger logger = LoggerFactory.getLogger(SocketSessionController.class);

	@Autowired
	private static User_TblService user_tbl_service;
	
    private static final ConcurrentHashMap<String, WebSocketSession> websocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
   
    public static synchronized void add(WebSocketSession session) {
        logger.debug("add Session : id = " + session.getId() + ", body = " + session.toString());
        websocketSessionMap.put(session.getId(), session);
    }
    
    public static synchronized void remove(WebSocketSession session) {
        logger.debug("remove Session : id = " + session.getId() + ", body = " + session.toString());
        websocketSessionMap.remove(session.getId());
    }
    
    public static void sendAllForWebsocket(String message) {
        logger.debug("send all : msg = " +  message);
        for(Map.Entry<String, WebSocketSession> em : websocketSessionMap.entrySet()){
            try {
                WebSocketSession session = em.getValue();
                   if(session.isOpen()){
                       synchronized (session) {
                           session.sendMessage(new TextMessage(message));
                       }
                   }
            } catch (IOException e) {
                   logger.error("sendAllForWebsocket...");
            }
        }
		User_Tbl user = user_tbl_service.getUserInfo("unbiari"); // 오류 발생 2016/01/02
		logger.debug("result = " + user.getUser_SeqNum());
    }
}
