package com.kidmobile.csr.game_tbl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.user_tbl.User_Tbl;
import com.kidmobile.csr.user_tbl.User_TblService;

@Controller
@RequestMapping("/game_tbl.do")
public class Game_TblCon {
	
	//@Inject
	//@Named("Game_TblService")
	@Autowired
	private Game_TblService service;
	
	//@Inject
	//@Named("User_TblService")
	@Autowired
	private User_TblService user_service;
	
	@RequestMapping(params = "method=init_lists") // called by GET/POST
	public String initLists() throws Exception {
		return "server/index_game";
	}
	
	@RequestMapping(params = "method=get_lists") // called by AJAX
	public void getLists( @RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int rowSize,
			HttpServletResponse response ) throws Exception {
		int i;
		
		response.setContentType("application/x-json; charset=UTF-8"); // 한글지원
		//response.setContentType("text/Xml;charset=UTF-8");
		
		int result_pages_count = service.getTotalRecords();
		List<Game_Tbl> resultPages = service.getLists(pageIndex, rowSize);
		Game_Tbl temp_item;
		
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
	public void create( Game_Tbl item, HttpServletResponse response ) throws Exception {
		service.create(item);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}
	
	@RequestMapping(params="method=update") // called by AJAX
	public void update( Game_Tbl item, HttpServletResponse response ) throws Exception {
		service.update(item);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}

	@RequestMapping(params="method=remove") // called by AJAX
	public void remove( @RequestParam(value = "game_SeqNum", defaultValue = "0") int id, HttpServletResponse response ) throws Exception {
		if( id != 0 )
			service.remove(id);
		
		JSONObject j_object = new JSONObject();
		j_object.put("success", true);
		
		response.getWriter().write(j_object.toString());
	}


	@RequestMapping(params="method=game_insert") // called by AJAX
	public void gameInsert( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String ret_str;
		Game_Tbl item = new Game_Tbl();
		User_Tbl user_item_master, user_item_guest;
		
		int val_master_add_point = 0;
		int val_guest_add_point = 0;
		
		item.setRoom_Name(request.getParameter("room_Name"));
		item.setMaster_ID(request.getParameter("master_ID"));
		item.setGuest_ID(request.getParameter("guest_ID"));
		item.setMaster_WinFlag(request.getParameter("master_WinFlag"));
		item.setMaster_Country(request.getParameter("master_Country"));
		item.setGuest_Country(request.getParameter("guest_Country"));
		
		// check duplicated...
		if( service.existSearchLists(item) ) {
			System.out.println("Game insert duplicated...");
			
			ret_str = "fail";		
			response.getWriter().write(ret_str);
			return;
		}
		
		//
		/*
		if( request.getParameter("master_GetGlod") != null )
			val_master_add_point = Integer.parseInt(request.getParameter("master_GetGlod"));
		else
			val_master_add_point = 0;
		item.setMaster_GetGold(val_master_add_point);		
		
		//
		if( request.getParameter("guest_GetGold") != null )
			val_guest_add_point = Integer.parseInt(request.getParameter("guest_GetGold"));
		else
			val_guest_add_point = 0;		
		item.setGuest_GetGold(val_guest_add_point);
		*/
		
		//
		if( request.getParameter("game_TimeLength") != null )
			item.setGame_TimeLength( Integer.parseInt(request.getParameter("game_TimeLength")));
		else
			item.setGame_TimeLength(0);

		// 20140817 - 2 감소필요
		if( (request.getParameter("game_TurnTotal") != null) && (Integer.parseInt(request.getParameter("game_TurnTotal")) > 2) )
			item.setGame_TurnTotal( Integer.parseInt(request.getParameter("game_TurnTotal")) - 2 );
		else
			item.setGame_TurnTotal(0);
		
		//
		if( request.getParameter("tournamentCount_Max") != null )
			item.setWinCount_Max( Integer.parseInt(request.getParameter("tournamentCount_Max")));
		else
			item.setWinCount_Max(0);
		
		//
		if( request.getParameter("tournamentCount_Now") != null )
			item.setWinCount_Now( Integer.parseInt(request.getParameter("tournamentCount_Now")));
		else
			item.setWinCount_Now(0);
		
		item.setGame_History(request.getParameter("game_History"));
		
		service.create(item);
		
		// update user info
		user_item_master = user_service.getUserInfo(request.getParameter("master_ID"));
		user_item_guest = user_service.getUserInfo(request.getParameter("guest_ID"));
		
		if( "Y".equals(item.getMaster_WinFlag()) || "DY".equals(item.getMaster_WinFlag()) ) {
			user_item_master.setCount_Total( user_item_master.getCount_Total() + 1 );
			user_item_master.setCount_Win( user_item_master.getCount_Win() + 1 );
			user_item_master.setCount_NowWin( user_item_master.getCount_NowWin() + 1);
			if( user_item_master.getCount_MaxWin() < user_item_master.getCount_NowWin() )
				user_item_master.setCount_MaxWin( user_item_master.getCount_NowWin() );
			
			user_item_guest.setCount_Total( user_item_guest.getCount_Total() + 1 );
			user_item_guest.setCount_Loss( user_item_guest.getCount_Loss() + 1 );
			user_item_guest.setCount_NowWin( 0 );
			
			val_master_add_point = 5;
			val_guest_add_point = 2;
		}
		else if( "N".equals(item.getMaster_WinFlag()) || "DN".equals(item.getMaster_WinFlag()) ) {
			user_item_master.setCount_Total( user_item_master.getCount_Total() + 1 );
			user_item_master.setCount_Loss( user_item_master.getCount_Loss() + 1 );
			user_item_master.setCount_NowWin( 0 );
			
			user_item_guest.setCount_Total( user_item_guest.getCount_Total() + 1 );
			user_item_guest.setCount_Win( user_item_guest.getCount_Win() + 1 );
			user_item_guest.setCount_NowWin( user_item_guest.getCount_NowWin() + 1);
			if( user_item_guest.getCount_MaxWin() < user_item_guest.getCount_NowWin() )
				user_item_guest.setCount_MaxWin( user_item_guest.getCount_NowWin() );
			
			val_master_add_point = 2;
			val_guest_add_point = 5;
		}
		user_item_master.setGold_Total( user_item_master.getGold_Total() + val_master_add_point);
		user_item_master.setGold_GetLast(val_master_add_point);
		user_item_guest.setGold_Total( user_item_guest.getGold_Total() + val_guest_add_point);
		user_item_guest.setGold_GetLast(val_guest_add_point);
		
		user_service.update(user_item_master);
		user_service.update(user_item_guest);
		
		// end...
		ret_str = "ok";
		
		response.getWriter().write(ret_str);
	}
}
