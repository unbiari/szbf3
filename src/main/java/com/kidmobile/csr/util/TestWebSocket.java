package com.kidmobile.csr.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class TestWebSocket extends AbstractWebSocketHandler{
    
    String path = "D:\\STS space\\WorkSpace\\STSWorkSpace\\chat\\src\\main\\webapp\\resources\\";
    String filename = null;
    
    /**
     * 일반 텍스트 메세지
     */
    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println();
        System.out.println("handleTextMessage");    
        System.out.println("msg : " + message.toString());
        System.out.println("getPayload : " + message.getPayload());
        System.out.println("getPayloadLength : " + message.getPayloadLength());
        System.out.println("asBytes to String : " + new String(message.asBytes()));
        System.out.println();
        
        // 업로드 파일명(클라이언트에서 파일 업로드하기전에 
        // filename:파일명 으로 먼저 메세지를 보내온다.
        if(message.getPayload().startsWith("filename:")){
            filename = message.getPayload().split(":")[1];
        }
    }
    
    /**
     * 파일 업로드
     * 기본 버퍼 사이즈가 8192바이트 인데 초기 설정 때 버퍼사이즈를 정해 줄 수 있다.
     * 버퍼 사이즈를 넘어가게 되면 웹소켓은 close 된다.
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        System.out.println();
        
        System.out.println("handleBinaryMessage : " + message);        
        ByteBuffer payload = message.getPayload();
        System.out.println(message.getPayloadLength());
        System.out.println();
        
        File file = new File(path+filename);
        
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file)) ){
            while(payload.hasRemaining()){
                bos.write(payload.get());
            }      
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }     
    }
    
    /**
     * ??????
     */
    @Override
    protected void handlePongMessage(WebSocketSession session,
            PongMessage message) throws Exception {
        // TODO Auto-generated method stub
        super.handlePongMessage(session, message);
        System.out.println();
        System.out.println("handlePongMessage");        
        System.out.println();
    }
    
    /**
     * 접속자가 발생했을 때 호출
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println();
        System.out.println("WebSocket Opened......");
        System.out.println();
    }
    
    /**
     * 웹소켓 연결이 끊어졌을때 호출되는 메소드
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,
            CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println();
        System.out.println("WebSocket Closed......" + status.toString());
        System.out.println();
    }
    
    /**
     * 요청이 들어 왔을때 종류(?)별로 호출하는 메소드를 정한다.
     * 파일 업로드가 진행되면 handleBinaryMessage 메소드를 호출
     * 일반 텍스트 메세지이면 handleTextMessage 메소드를 호출
     * super.handleMessage(session, message);
     * 를 작성하지 않으면 handleBinaryMessage, handleTextMessage 를 호출하지 않는다.
     */
    @Override
    public void handleMessage(WebSocketSession session,
            WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        System.out.println();
        System.out.println("handleMessage");
        System.out.println();
    }
    
    /**
     * ?????????
     */
    @Override
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        // TODO Auto-generated method stub
        super.handleTransportError(session, exception);
    }
    
}
