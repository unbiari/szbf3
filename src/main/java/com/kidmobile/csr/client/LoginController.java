/**
 * 
 */
package com.kidmobile.csr.client;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.global.Constant;
import com.kidmobile.csr.global.ConstantCountry;
import com.kidmobile.csr.global.ConstantStr;
import com.kidmobile.csr.user_tbl.User_Tbl;
import com.kidmobile.csr.user_tbl.User_TblService;
import com.kidmobile.csr.util.ChangeLocaleController;
import com.kidmobile.csr.util.PreventInjection;


@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
	@Named("User_TblService")
	private User_TblService user_tbl_service;
	
	@Inject
	private ChangeLocaleController changeLocaleController;	

	@Autowired ShaPasswordEncoder sha_encoder;
	@Autowired Md5PasswordEncoder md5_encoder;

	// 테스트 할때 사용
	@RequestMapping(value = "/socket.do", method = RequestMethod.GET)
	public String socket() {
		logger.info(ConstantStr.This_func_called_str);
		
		return "socket";
	}
	@RequestMapping(value = "/socket2.do", method = RequestMethod.GET)
	public String socket2() {
		logger.info(ConstantStr.This_func_called_str);
		
		return "socket2";
	}

	// 테스트 할때 사용
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String test() {
		logger.info(ConstantStr.This_func_called_str);
		
		return "client/test_js";
	}
	
	// 맴버 추가 Get 함수
	@RequestMapping(value = "/insertMbr.do", method = RequestMethod.GET)
	public String insertMbr(Model model) {
		logger.info(ConstantStr.This_func_called_str);
		
		model.addAttribute( "countryList", ConstantCountry.Country_str );
		
		return "client/insertMbr_js";
	}
	
	// 맴버 추가 POST 함수 : 저장할때 불림
	@RequestMapping(value = "/insertMbr.do", method = RequestMethod.POST)
	public void insertMbr_Save(HttpServletRequest request, HttpServletResponse response) throws IOException { // call from AJAX
		logger.info(ConstantStr.This_func_called_str);
		
		String user_id = request.getParameter("userId");
		String user_pw = request.getParameter("userPass");
		String country_code = request.getParameter("countryCode");
		String user_phone = request.getParameter("userPhone");
		
		response.setContentType("text/html; charset=UTF-8"); // 한글지원
		response.setHeader("cache-control", "no-cache,no-store");
		
		JSONObject j_object = new JSONObject();
		
		if( PreventInjection.ValidUserId(user_id) == false ) {
			j_object.put("RT", "1"); // 1 보다 크면 오류...
			j_object.put("msg", "userId error");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		if( PreventInjection.ValidPass(user_pw) == false ) {
			j_object.put("RT", "1");
			j_object.put("msg", "userPass error");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		if( country_code.length() != 3 ) {
			j_object.put("RT", "1");
			j_object.put("msg", "countryCode error");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		if( user_phone != null && !"".equals(user_phone) ) {
			if( PreventInjection.ValidPhone(user_phone) == false ) {
				j_object.put("RT", "1");
				j_object.put("msg", "userPhone error");
				
				response.getWriter().write(j_object.toString());
				return;
			}
		}
		
		String user_pw_sha = sha_encoder.encodePassword(user_pw, null);
		
		User_Tbl temp_user = new User_Tbl();
		temp_user.setUser_ID(user_id);
		temp_user.setUser_PW(user_pw_sha);
		temp_user.setUser_Country(country_code);
		temp_user.setUser_TelNum(user_phone);
		
		user_tbl_service.create(temp_user);
		
		// 다시 저장한 것을 읽어서 확인
		User_Tbl item = user_tbl_service.getUserInfo(user_id);
		if( item == null ) {
			j_object.put("RT", "1");
			j_object.put("msg", "user create error");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		
		j_object.put("RT", "0"); // 0 이면 성공...
		j_object.put("msg", "user insert success");			
		j_object.put("seqNum", item.getUser_SeqNum()); // 모든 곳에서 사용
		
		response.getWriter().write(j_object.toString());
		
		return;
	}
	
	@RequestMapping(value = "/loginPre.do") // called first time... en_US / ko_KR / cn_ZH
	public String login_pre( @RequestParam(value = "locale", defaultValue = "en_US") String newLocale,
			HttpServletRequest request, HttpServletResponse response ) {
		logger.info(ConstantStr.This_func_called_str);
		
		changeLocaleController.change(request, response, newLocale);
		
		return "client/login_js";
	}

	@RequestMapping(value = "/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info(ConstantStr.This_func_called_str);

		String user_id = request.getParameter("userId");
		String user_pw = request.getParameter("userPass");
		
		response.setContentType("text/html; charset=UTF-8"); // 한글지원
		response.setHeader("cache-control", "no-cache,no-store");
		
		JSONObject j_object = new JSONObject();
		
		if( PreventInjection.ValidUserId(user_id) == false ) {
			j_object.put("RT", "1"); // 1 보다 크면 오류...
			String msg = changeLocaleController.getLocaleMessage("error.no_id_or_pw");
			j_object.put("msg", msg);
			
			response.getWriter().write(j_object.toString());
			return;
		}
		if( PreventInjection.ValidPass(user_pw) == false ) {
			j_object.put("RT", "1");
			j_object.put("msg", "userPass error");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		
		User_Tbl item = user_tbl_service.getUserInfo(user_id);
		if( item == null ) {
			j_object.put("RT", "1");
			j_object.put("msg", "no user");
			
			response.getWriter().write(j_object.toString());
			return;
		}
		
		// 정상적인 경우 처리
		String user_pw_sha = sha_encoder.encodePassword(user_pw, null);
		String user_pw_md5 = md5_encoder.encodePassword(user_pw, null);
		
		logger.info(user_pw_sha);
		logger.info(user_pw_md5);
		
		if( user_pw_sha.equals(item.getUser_PW()) || user_pw_md5.equals(item.getUser_PW()) ) {
			j_object.put("RT", "0"); // 0 이면 성공...
			j_object.put("msg", "login success");
			j_object.put("seqNum", item.getUser_SeqNum()); // 모든 곳에서 사용
		}
		else {
			j_object.put("RT", "1");
			j_object.put("msg", "password error");			
		}
		
		response.getWriter().write(j_object.toString());
		return;
	}

	@RequestMapping(value = "/clientMain.do", method = RequestMethod.GET)
	public String clientMain(HttpServletRequest request, Model model) {
		logger.info(ConstantStr.This_func_called_str);
		
		String seq_num = request.getParameter("seqNum");
		
		model.addAttribute( "seqNum", seq_num );
		return "client/main_js";
	}

	// 작업 진행중 함수들...
	@RequestMapping(value = "/pwdSearch.do")
	public String pwdSearch(Model model) {
		logger.info(ConstantStr.This_func_called_str);
		
		return "client/pwdSearch_jqm";
	}

}
