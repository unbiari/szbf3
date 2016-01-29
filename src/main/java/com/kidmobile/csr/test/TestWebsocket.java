package com.kidmobile.csr.test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

@ServerEndpoint("/websocket")
public class TestWebsocket {
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