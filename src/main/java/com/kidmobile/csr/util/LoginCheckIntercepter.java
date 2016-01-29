package com.kidmobile.csr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class LoginCheckIntercepter extends HandlerInterceptorAdapter {
 
    //HandlerInterceptorAdapter 를 상속 받아야 intercepter 등록이 가능함
    private static final Logger logger  =  Logger.getLogger(LoginCheckIntercepter.class);
 
    /*
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
        boolean result   =  false;
        String rootPath   =  request.getContextPath();
        
        try{
            logger.debug("enter intercepter, rootPath = " + rootPath);
            HttpSession session  =  request.getSession(false);
            
            // session non exist
            if(session == null){
                //index.jsp 로 이동, web.xml 에 설정 되어 있음 (<welcome-file-list> 태그)
                response.sendRedirect(rootPath);
                return false;
            }else{
                //UserInfo 로 세션 등록
                //UserDTO userDTO  =  (UserDTO)session.getAttribute("userInfo");
                
                // session exist
               // if(userDTO != null && userDTO.getUSER_ID() != null){
                 
                //}else{// session non exist
                    response.sendRedirect(rootPath);
                    return false;
                //}
            }
            //result =  true;
        }catch(Exception e){
            e.printStackTrace();
            logger.debug(e.getMessage());
            return false;
        }
    }
    */
}

