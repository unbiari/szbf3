package com.kidmobile.csr.szbf;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kidmobile.csr.user_tbl.User_Tbl;
import com.kidmobile.csr.user_tbl.User_TblService;
import com.kidmobile.csr.user_tbl.User_TblServiceImpl;

import net.sf.json.JSONObject;

@Controller
@ServerEndpoint("/socketServer")
public class SocketSrvController {
	private static final Logger logger = LoggerFactory.getLogger(SocketSrvController.class);
	
	@Autowired
	private User_TblService user_tbl_service;

	static Set<Session> sessionUsers = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void handleOpen(Session userSession){
		sessionUsers.add(userSession);
	}
	
	@OnMessage
	public void handleMessage(String message,Session userSession) throws IOException{
		String username = (String)userSession.getUserProperties().get("username");
		if(username == null){
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as " + message));
		}else{
			Iterator<Session> iterator = sessionUsers.iterator();
			while(iterator.hasNext()){
				iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
			}
		}
		// int result = user_tbl_service.checkUserID("unbiari");
		User_TblService user_tbl_service = (User_TblService)new User_TblServiceImpl();
		User_Tbl user = user_tbl_service.getUserInfo("unbiari"); // 오류 발생 2016/01/02
		logger.debug("result = " + user.getUser_SeqNum());
	}
	
	@OnClose
	public void handleClose(Session userSession){
		sessionUsers.remove(userSession);
	}
	
	public String buildJsonData(String username,String message){
		JSONObject j_object = new JSONObject();
		
		j_object.put("username", username);
		j_object.put("message", message);
		
		return j_object.toString();
	}
}