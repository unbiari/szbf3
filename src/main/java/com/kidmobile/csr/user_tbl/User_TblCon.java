package com.kidmobile.csr.user_tbl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.game_tbl.Game_Tbl;
import com.kidmobile.csr.game_tbl.Game_TblService;

@Controller
@RequestMapping("/user_tbl.do")
public class User_TblCon {
	@Autowired private ServletContext servletContext;
	@Autowired Md5PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(User_TblCon.class);
	
	@Inject
	@Named("User_TblService")
	private User_TblService service;
	
	@Inject
	@Named("Game_TblService")
	private Game_TblService game_service;
	
	@RequestMapping(params = "method=init_lists") // called by GET/POST
	public String initLists() throws Exception {
		return "server/index_user";
	}
	
	@RequestMapping(params = "method=get_lists") // called by AJAX
	public void getLists( @RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int rowSize,
			HttpServletResponse response ) throws Exception {
		int i;
		
		logger.info("... count = " + (Integer)servletContext.getAttribute("count"));
		HashMap hash_map = (HashMap)servletContext.getAttribute("hash_map_s");
		if( hash_map != null )
			logger.info("... hash_map_s = " + hash_map.toString());
		
		response.setContentType("application/x-json; charset=UTF-8"); // 한글지원
		//response.setContentType("text/Xml;charset=UTF-8");
		
		int result_pages_count = service.getTotalRecords();
		List<User_Tbl> resultPages = service.getLists(pageIndex, rowSize);
		User_Tbl temp_item;
		
		for(i=0; i<resultPages.size(); i++ ) {
			temp_item = resultPages.get(i);
		}
		
		JSONArray j_array = null;
		JSONObject j_object = new JSONObject();
		
		j_array = JSONArray.fromObject(resultPages);
		
		j_object.put("rows", j_array);
		j_object.put("total", result_pages_count);
		
		//response.getWriter().write(j_array.toString());
		response.getWriter().write(j_object.toString());
	}

	@RequestMapping(params="method=create") // called by AJAX
	public void create( User_Tbl item, HttpServletResponse response ) throws Exception {
		service.create(item);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}
	
	@RequestMapping(params="method=update") // called by AJAX
	public void update( User_Tbl item, HttpServletResponse response ) throws Exception {
		service.update(item);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}

	@RequestMapping(params="method=remove") // called by AJAX
	public void remove( @RequestParam(value = "user_SeqNum", defaultValue = "0") int id, HttpServletResponse response ) throws Exception {
		if( id != 0 )
			service.remove(id);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}
	
	
	@RequestMapping(params="method=user_check") // called by AJAX
	public void userCheck( @RequestParam(value = "user_ID") String user_id, HttpServletResponse response ) throws Exception {
		String ret_str;
		
		//response.setContentType("text/Xml;charset=UTF-8");
		
		if( (user_id == null) || (user_id == "") ) {
			ret_str = "fail";
		}
		else {
			if( service.checkUserID(user_id) == 0 )
				ret_str = "ok"; // user_id use enable...
			else
				ret_str = "fail";
		}
		response.getWriter().write(ret_str);
	}
	
	@RequestMapping(params="method=user_insert") // called by AJAX
	public void userInsert( @RequestParam(value = "user_ID") String user_id,
			@RequestParam(value = "user_PW") String user_pw,
			@RequestParam(value = "user_Country") String user_country,
			@RequestParam(value = "gold_GetLast") String gold_get_last,
			HttpServletResponse response ) throws Exception {
		String ret_str;
		
		//response.setContentType("text/Xml;charset=UTF-8");
		
		if( (user_id == null) || (user_id == "") || 
			(user_pw == null) || (user_pw == "") ||
			(user_country == null) || (user_country == "") ||
			(gold_get_last == null) || (gold_get_last == "") ) {		
			ret_str = "fail";
		}
		else {
			User_Tbl item = new User_Tbl();
			String user_pw_md5 = passwordEncoder.encodePassword(user_pw, null);
			int temp_gold = Integer.parseInt(gold_get_last);
			
			item.setUser_ID(user_id);
			item.setUser_PW(user_pw_md5);
			item.setUser_Country(user_country);
			item.setGold_Total(temp_gold);
			item.setGold_GetLast(temp_gold);
			
			service.create(item);
			
			ret_str = "ok";
		}
		response.getWriter().write(ret_str);
	}

	@RequestMapping(params="method=user_info") // called by AJAX
	public void userInfo( @RequestParam(value = "user_ID") String user_id,
			HttpServletResponse response ) throws Exception {
		String ret_str;
		
		response.setContentType("text/Xml;charset=UTF-8"); // 20140817 한글 출력 enable
		
		if( (user_id == null) || (user_id == "") ) {
			ret_str = "fail";
		}
		else {
			User_Tbl item = service.getUserInfo(user_id);
			
			if( item != null ) {
				int i;
				ret_str = "ok;" + item.getUser_ID() + ";" + item.getUser_Level() + ";" + item.getUser_Country() + ";" + 
						item.getCount_Total() + ";" + item.getCount_Win() + ";" + item.getCount_Loss() + ";" + 
						item.getCount_MaxWin() + ";" + item.getCount_NowWin() + ";" + item.getGold_Total();

				// 20140817 start
				List<Game_Tbl> resultPages = game_service.getRecentLists(5); // 5개의 data 를 얻어옴
				Game_Tbl temp_item;
				
				for(i=0; i<resultPages.size(); i++ ) {
					ret_str += ";"+ resultPages.get(i).getMaster_WinFlag() + "," + resultPages.get(i).getMaster_Country() + "," + 
						resultPages.get(i).getGuest_Country() + "," + resultPages.get(i).getGame_History();
				}
				System.out.println(ret_str); // 20140817
				// end
			}
			else
				ret_str = "fail";
		}
		response.getWriter().write(ret_str);
	}

	@RequestMapping(params="method=user_login") // called by AJAX
	public void userLogin( @RequestParam(value = "user_ID") String user_id,
			@RequestParam(value = "user_PW") String user_pw,
			@RequestParam(value = "gold_GetLast") String gold_get_last,
			HttpServletResponse response ) throws Exception {
		String ret_str;
		
		//response.setContentType("text/Xml;charset=UTF-8");
		
		if( (user_id == null) || (user_id == "") || (user_pw == null) || (user_pw == "") || (gold_get_last == null) || (gold_get_last == "") ) {
			ret_str = "fail";
		}
		else {
			User_Tbl item = service.getUserInfo(user_id);
			if( item != null ) {
				String user_pw_md5 = passwordEncoder.encodePassword(user_pw, null);
				if( user_pw_md5.equals(item.getUser_PW()) == true ) {
					int temp_gold = Integer.parseInt(gold_get_last);
					
					/* 사용하지 않음
					item.setGold_Total( item.getGold_Total() + temp_gold );
					item.setGold_GetLast( temp_gold );
					service.update(item);
					*/
					
					ret_str = "ok";
				}
				else
					ret_str = "fail";
			}
			else
				ret_str = "fail";
		}
		response.getWriter().write(ret_str);
	}
}
