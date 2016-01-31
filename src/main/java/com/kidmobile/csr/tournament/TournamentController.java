package com.kidmobile.csr.tournament;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.global.AtomRoom;
import com.kidmobile.csr.global.AtomRoomArray;
import com.kidmobile.csr.global.AtomUser;
import com.kidmobile.csr.global.AtomUserArray;
import com.kidmobile.csr.global.Constant;
import com.kidmobile.csr.global.Constant.RoomStatus;
import com.kidmobile.csr.global.Constant.GameUserStatus;
import com.kidmobile.csr.global.Constant.TournamentStatus;
import com.kidmobile.csr.global.ConstantStr;
import com.kidmobile.csr.global.MemoryRoom;
import com.kidmobile.csr.global.Room;
import com.kidmobile.csr.global.Tournament;
import com.kidmobile.csr.global.GameUser;
import com.kidmobile.csr.util.BigDataTreat;
import com.kidmobile.csr.util.PreventInjection;

@Controller
@RequestMapping("/client.do")
public class TournamentController {
	@Autowired private ServletContext servletContext;
	
	private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);

    private static final HashMap<String, GameUser> gameUserMap = new HashMap<String, GameUser>();
    private static final HashMap<String, Tournament> tournamentMap = new HashMap<String, Tournament>(); // 자신의 tournament 찾음, 초기화가 된것만 등록
    private static final HashMap<String, Room> roomMap = new HashMap<String, Room>(); // 자신의 room 을 찾음
    
    private static Tournament Now_tournament_1 = new Tournament(); // 2강의 현재 tournament, 처음에 한개 생성함
    private static Tournament Now_tournament_2 = new Tournament(); // 4강의 현재 tournament, 처음에 한개 생성함
    private static Tournament Now_tournament_3 = new Tournament(); // 8강의 현재 tournament, 처음에 한개 생성함
    private static Tournament Now_tournament_x = new Tournament(); // 게임 대회 현재 tournament, 처음에 한개 생성함
    
	// Tomcat 서비스를 시작하면서 초기화 함
	@PostConstruct
	public void init() {
		logger.info("init()");
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(params = "blind_match", method = RequestMethod.GET) // game init start
	public String blind_match(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num, HttpServletRequest request, Model model) {
		logger.info("blind_match:seqNum="+seq_num );
		
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			model.addAttribute( ConstantStr.STR_SEQ_NUM, "0" ); // error
		}
		else {
	        HttpSession session  =  request.getSession(false);
			GameUser user = gameUserMap.get(seq_num);
			
			if(user == null) { 
				// user 새로 생성 : 정상적인 경우
				user = new GameUser(seq_num, session.getId());
				
				gameUserMap.put(seq_num, user);
				model.addAttribute( ConstantStr.STR_SEQ_NUM, seq_num ); // 모든 jsp 에서 사용	
			}
			else if( user.getSessionId() == session.getId() ) {
				// 끊어졌다 새로 접속
				logger.warn("blind_match:sesseion valid.");
				model.addAttribute( ConstantStr.STR_GAME_USER_STATUS, user.getGameUserStatus());
				model.addAttribute( ConstantStr.STR_SEQ_NUM, seq_num ); // 모든 jsp 에서 사용	
			}
			else {
				logger.warn("blind_match:sesseion invalidate.");
				model.addAttribute( ConstantStr.STR_SEQ_NUM, "0" ); // error
			}
			user.setUpdatedTime(System.currentTimeMillis());
		}
		
		return "tournament/start_js";
	}

	@RequestMapping(params = "join_blind") // called by AJAX
	public void join_blind(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num, 
			@RequestParam(value = "endWin") String endWin, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("join_blind:seqNum="+seq_num);
		
		JSONObject j_object = new JSONObject();
		Tournament temp_tournament = null;
		Room temp_room = null;
		
		//response.setContentType("text/html;charset=UTF-8");	
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "seq_num error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// String key = seq_num;
		int end_win_count = Integer.parseInt(endWin);
		
		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_USER_NONE ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정
		
		if( user.getTournamentKey() != null || user.getRoomKey() != null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// Tournament 초기 호출
		if(end_win_count == 1) { // 1:1 game
			synchronized(Now_tournament_1) {
				if(Now_tournament_1.getTouramentStatus() == Constant.TournamentStatus.STATUS_TOURNAMENT_NONE) {
					Now_tournament_1.Initialize(seq_num, 0, end_win_count); // room 이 자동 생성됨
					tournamentMap.put(seq_num, Now_tournament_1);
					
					Now_tournament_1.getRoomKeyList().add(seq_num);
				}
				
				user.setTournamentKey(Now_tournament_1.getTournamentKey());
				Now_tournament_1.setNowUserCount(Now_tournament_1.getNowUserCount()+1);
				Now_tournament_1.setUpdatedTime(System.currentTimeMillis());
				
				temp_tournament = Now_tournament_1; //이후 새로 생성되기 이전 것을 저장하여 사용함
				if(Now_tournament_1.getMaxUserCount() == Now_tournament_1.getNowUserCount()) {
					Now_tournament_1.setTouramentStatus(TournamentStatus.STATUS_TOURNAMENT_FULLED);
					
					Now_tournament_1 = new Tournament(); // 신규 비어있는 tournament 생성
				}
			}
		}
		else if(end_win_count == 2) {	
		}
		else if(end_win_count == 3) {
		}
		else {
		}
		
		user.setGameUserStatus(GameUserStatus.STATUS_TOURNAMENT_JOINED);
		
		// Room
		if(end_win_count == 1) {
			temp_room = temp_tournament.getNowRoom0();
			
			synchronized(temp_room) {
				if(temp_room.getRoomStatus() == RoomStatus.STATUS_ROOM_NONE) {
					// master 설정 : 0 승 room 초기화 하여 사용할 수 있게 함
					temp_room.Initialize(seq_num); // room status 가 STATUS_ROOM_INITIALIZED 로 바뀜
					roomMap.put(seq_num, temp_room);
					
					temp_tournament.getRoomKeyList().add(seq_num);
					
					// ret
					j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
					j_object.put(ConstantStr.STR_MSG, "game created as room master");
					j_object.put("my", "master");
				}
				else if(temp_room.getRoomStatus() == RoomStatus.STATUS_ROOM_INITIALIZED) {
					// slave 설정
					temp_room.setSeqNumSlave(seq_num);
					temp_room.setRoomStatus(RoomStatus.STATUS_BOARD_REPLACING);
					temp_room.setNowTurnCount(Constant.TURN_COUNT_START + 0);
					
					// ret string
					j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
					j_object.put(ConstantStr.STR_MSG, "game created, i am room slave");
					j_object.put("my", "slave");
					j_object.put("master", temp_room.getSeqNumMaster());
					j_object.put("room", temp_room.getRoomKey());
					
					// 새로운 room 을 생성해 놓음
					temp_tournament.setNowRoom0(new Room()); // 새로운 room 생성해 놓음
				}
				temp_room.setUpdatedTime(System.currentTimeMillis());
				
				user.setRoomKey(temp_room.getRoomKey());
			}
		}
		else if(end_win_count == 2) {
		}
		else if(end_win_count == 3) {
		}
		else {
		}
		
		// 
		response.getWriter().write(j_object.toString());
		return; // exit
	}

	@RequestMapping(params = "check_room_created") // 오직 master 만 호출하는 함수, called by AJAX
	public void check_room_created(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("check_room_created:seqNum="+seq_num);
		
		JSONObject j_object = new JSONObject();
		
		//response.setContentType("text/html;charset=UTF-8");	
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "seq_num error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정
		
		// Room
		if( user.getTournamentKey() == null && user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		if( room.getRoomStatus() == RoomStatus.STATUS_ROOM_INITIALIZED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "now room not created");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// master setting
		room.setRoomStatus(RoomStatus.STATUS_BOARD_REPLACING);
		room.getHistoryArr()[0] = room.getSeqNumMaster() + ":" + room.getSeqNumSlave();
		
		// ret
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "game created, i am room master");
		j_object.put("my", "master");
		j_object.put("slave", room.getSeqNumSlave());
		j_object.put("room", room.getRoomKey());
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}

	@RequestMapping(params = "send_replace") // called by AJAX
	public void send_replace(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num,
			@RequestParam(value = "replace") String replace, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		logger.info("send_replace:seqNum="+seq_num+",replace="+replace);
		
		int i;
		byte mal; // -1...-9:slave, 1...9:master
		String history_str;
		
		JSONObject j_object = new JSONObject();
		
		//response.setContentType("text/html;charset=UTF-8");	
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "replace error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정

		// Room
		if( user.getTournamentKey() == null || user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		if( room.getRoomStatus() != RoomStatus.STATUS_BOARD_ONE_REPLACED && room.getRoomStatus() != RoomStatus.STATUS_BOARD_REPLACING ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "now room not created");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// check master or slave
		if( room.getSeqNumMaster().equals(seq_num) ) { // master
			for( i=0; i<Constant.MAL_MAX; i++ ) {
				mal = (byte)(replace.charAt(i+1)-'0');
				room.getRoomPan()[ i + Constant.MASTER_INDEX_START ] = mal;				
			}
			history_str = "PO" + replace.substring(1);
			room.setNowTurnCount(room.getNowTurnCount()+1); // 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
			room.getHistoryArr()[1] = history_str; // 1 is master
		}
		else { // slave
			for( i=0; i<Constant.MAL_MAX; i++ ) {
				mal = (byte)(replace.charAt(i+1)-'0');
				room.getRoomPan()[ Constant.SLAVE_INDEX_START - i  ] = (byte) -mal; // from 1				
			}
			history_str = "PO" + replace.substring(1);
			room.setNowTurnCount(room.getNowTurnCount()+1); // 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
			room.getHistoryArr()[2] = history_str; // 2 is slave
		}
		
		if(room.getRoomStatus() == RoomStatus.STATUS_BOARD_REPLACING)
			room.setRoomStatus(RoomStatus.STATUS_BOARD_ONE_REPLACED);
		else {
			room.setRoomStatus(RoomStatus.STATUS_ROOM_GAME_ING);
		}
		
		// ret
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "send_replace success");
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}

	@RequestMapping(params = "check_game_start") // master 와 client 모두 호출, called by AJAX
	public void check_game_start(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num, HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		logger.info("check_game_start:seqNum="+seq_num);
		
		JSONObject j_object = new JSONObject();
		
		//response.setContentType("text/html;charset=UTF-8");	
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game start error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정

		// Room
		if( user.getTournamentKey() == null || user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		// 
		if( room.getRoomStatus() != RoomStatus.STATUS_ROOM_GAME_ING ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_NOW_TURN_COUNT, room.getNowTurnCount()); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "now room not created");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// ret
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "match start");
		j_object.put("now_turn_count", room.getNowTurnCount());
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}
	
	// utility function
	private int reverse_index( int index ) {
		return Constant.PAN_SIZE + 1 - index;
	}
	
	private Boolean is_win( int attack_mal_id, int defence_mal_id )
	{
		int i;
		int attack_mal_index = Math.abs(attack_mal_id) - 1; // from index 0
		int abs_defence_mal_id = Math.abs(defence_mal_id);
				
		if( Constant.Attack_win[attack_mal_index].length == 0 )
			return false;
			
		for(i=0; i<Constant.Attack_win[attack_mal_index].length; i++)
			if( Constant.Attack_win[attack_mal_index][i] == abs_defence_mal_id )
				return true;
				
		return false;
	}

	private String make_other_mal_str( Boolean master_flag, Room room ) { // 게이머 들에게 보낼 데이타, history 에 저장하지 않음
		String ret_str = "";
		byte temp_mal;
		
		for( int i=1; i<=Constant.PAN_SIZE; i++ ) { // start i is 1
			temp_mal = room.getRoomPan()[i];
			
			if( master_flag ) {
				if( temp_mal < 0 )
					ret_str += String.valueOf(i + Constant.INDEX_ADD) + String.valueOf(-temp_mal); // String.format( "%02d", i ) + -temp_mal;				
			}
			else {
				if( temp_mal > 0 )
					ret_str += String.valueOf( reverse_index(i) + Constant.INDEX_ADD ) + String.valueOf(temp_mal); // String.format( "%02d", reverse_index(i) ) + temp_mal;								
			}
		}
		
		return ret_str;
	}

	// 게임에서 action 을 한 사람에게만 호출됨
	private String parse_action( Boolean master_flag, String action, GameUser user, Room room, JSONObject j_object ) {
		logger.info("parse_action:master_flag="+master_flag+",action="+action);
		
		int from, to, from_master, to_master;
		String ret_str = null;
		String history_str = null;
		String spy_value = "";

		if( action.length() == 4) { // T105
			ret_str = "TO" + action.substring(1);
			history_str = ret_str;
			
			room.setNowTurnCount(room.getNowTurnCount()+1); // 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
			room.getHistoryArr()[ room.getNowTurnCount() - Constant.TURN_COUNT_START ] = history_str;
			
			return ret_str;
		}
		
		from = Integer.parseInt( action.substring(4, 6) ) - Constant.INDEX_ADD; // F1032315
		to = Integer.parseInt( action.substring(6, 8) ) - Constant.INDEX_ADD;
		
		if( master_flag ) {
			from_master = from;
			to_master = to;	
		}
		else {
			from_master = reverse_index( from );
			to_master = reverse_index( to );		
		}
		
		// C:P782951634,O:P928356174,O:M1022823,C:M1032721,O:M1042318,C:T105,O:F1061813,C:F1073127";
		byte[] room_pan = room.getRoomPan();
		
		switch( action.charAt(0) ) {
		case 'M': // MOVE
			if( master_flag ) {
				if( (room_pan[ from_master ] <= Constant.MalType.MAL_NONE) || (room_pan[ to_master ] != Constant.MalType.MAL_NONE) )
					break; // end logic error...
				
				ret_str = "MV" + action.substring(1);
				history_str = ret_str;
			}
			else {
				if( (room_pan[ from_master ] >= Constant.MalType.MAL_NONE) || (room_pan[ to_master ] != Constant.MalType.MAL_NONE) )
					break; // end logic error...
				
				ret_str = "MV" + action.substring(1);
				//ret_str = "MV" + action.substring(1, 4) + String.format("%02d", (from_master + Constant.INDEX_ADD)) + String.format("%02d", (to_master + Constant.INDEX_ADD));
				history_str = "MV" + action.substring(1, 4) + String.valueOf(from_master + Constant.INDEX_ADD) + String.valueOf(to_master + Constant.INDEX_ADD);
			}
		
			// logic change...
			room_pan[ to_master ] = room_pan[ from_master ];
			room_pan[ from_master ] = Constant.MalType.MAL_NONE;
			
			break;
			
		case 'F':
			if( master_flag ) {
				if( (room_pan[ from_master ] <= Constant.MalType.MAL_NONE) || (room_pan[ to_master ] >= Constant.MalType.MAL_NONE) )
					break; // end logic error...
			}
			else {
				if( (room_pan[ from_master ] >= Constant.MalType.MAL_NONE) || (room_pan[ to_master ] <= Constant.MalType.MAL_NONE) )
					break; // end logic error...
			}
			
			if( is_win( room_pan[from_master], room_pan[to_master] ) ) {
				if( Math.abs(room_pan[to_master]) == Constant.MalType.MAL_KING ) {
					room.setRoomStatus(RoomStatus.STATUS_ROOM_GAME_ENDED);
					user.setGameUserStatus(GameUserStatus.STATUS_USER_NONE);
					user.setRoomKey(null);
					user.setTournamentKey(null);
					
					if( master_flag )
						ret_str = "MW"; // master win
					else
						ret_str = "ML"; // master loss
					
					history_str = ret_str;
				}
				else {
					ret_str = "FW"; // from 쪽이 이김
					history_str = "FW"; // from 쪽이 이김
				}
			}
			else {
				if( Math.abs(room_pan[from_master]) == Constant.MalType.MAL_KING ) {
					room.setRoomStatus(RoomStatus.STATUS_ROOM_GAME_ENDED);
					user.setGameUserStatus(GameUserStatus.STATUS_USER_NONE);
					user.setRoomKey(null);
					user.setTournamentKey(null);
					
					if( master_flag )
						ret_str = "ML"; // master loss
					else
						ret_str = "MW"; // master win
					
					history_str = ret_str;
				}
				else {
					ret_str = "FL"; // to 쪽이 이김
					history_str = "FL"; // to 쪽이 이김
				}
			}
			
			ret_str += action.substring(1);
			if( master_flag )
				history_str = ret_str;
			else
				history_str += action.substring(1, 4) + String.valueOf(from_master + Constant.INDEX_ADD) + String.valueOf(to_master + Constant.INDEX_ADD);
			
			if( room.getRoomStatus() == RoomStatus.STATUS_ROOM_GAME_ENDED ) { // 게임이 종료 되면 상대 말을 보여줌
				j_object.put("last_position", make_other_mal_str( master_flag, room ));
			}
			else {
				// SPY OPEN 공격한 자에게 상대말을 알려줌
				if( Math.abs(room_pan[from_master]) == Constant.MalType.MAL_SPY ) { // SP249 -> 9
					spy_value = String.valueOf(Math.abs(room_pan[to_master]));
					
					if( master_flag == true )
						j_object.put("spy", String.valueOf(to_master + Constant.INDEX_ADD) + spy_value );				
					else // slave
						j_object.put("spy", String.valueOf(reverse_index(to_master) + Constant.INDEX_ADD) + spy_value );				
				}
				// spy 가 spy 를 공격한 경우에, 숫자가 하나만 추가 되도록 함, 해서 else if 를 사용함
				// SPY save to history : 타겟이 된 말이 spy 인 경우, 상대편에세 알려 주기 위해 history 에 저장해 놓음( check_game_action->turn_count_action 에서 사용 )
				else if( Math.abs(room_pan[to_master]) == Constant.MalType.MAL_SPY ) { // SP249 -> 9
					spy_value = String.valueOf(Math.abs(room_pan[from_master]));
				}
				
				// make histroy by spy_value
				if( !"".equals(spy_value) ) {
					if( (Math.abs(room_pan[from_master]) == Constant.MalType.MAL_SPY) && Math.abs(room_pan[to_master]) == Constant.MalType.MAL_SPY )
						history_str += "B" + spy_value; // B9 : 양쪽 모두 spy 인 경우
					else if( master_flag == true ) {
						if( Math.abs(room_pan[from_master]) == Constant.MalType.MAL_SPY )
							history_str += "M" + spy_value; // M9 : master 알게된 말
						else
							history_str += "S" + spy_value; // S9 : slave 알게된 말
					}
					else {
						if( Math.abs(room_pan[from_master]) == Constant.MalType.MAL_SPY )
							history_str += "S" + spy_value; // S9 : slave 알게된 말
						else
							history_str += "M" + spy_value; // M9 : master 알게된 말
					}
				}
					
				// logic change...
				if( is_win( room_pan[from_master], room_pan[to_master] ) )
					room_pan[ to_master ] = Constant.MalType.MAL_NONE;
				else
					room_pan[ from_master ] = Constant.MalType.MAL_NONE;
			}
			break;
			
		/* 앞에서 처리.
		case 'T': // TURN OVER
			ret_str = "TO" + action.substring(1);
			break;
		*/
			
		default:
			break;	
		}
		
		if( ret_str != null ) {
			room.setNowTurnCount(room.getNowTurnCount()+1); // 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
			room.getHistoryArr()[ room.getNowTurnCount() - Constant.TURN_COUNT_START ] = history_str;
		}
		
		return ret_str;
	}
	
	// only action person call
	@RequestMapping(params = "send_game_action") // called by AJAX
	public void send_game_action(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num,
			@RequestParam(value = "action") String action, @RequestParam(value = "turnCount") String turn_count_str, HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		logger.info("send_game_action:seqNum="+seq_num+",action="+action);
		
		Boolean master_flag;
		String ret_action;
		
		JSONObject j_object = new JSONObject();
		
		// 기본 check start
		if( (PreventInjection.ValidSeqNum(seq_num) == false) || PreventInjection.ValidTurnCount(turn_count_str) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "check action error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정

		// Room
		if( user.getTournamentKey() == null || user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		// check room status
		if( room.getRoomStatus() != RoomStatus.STATUS_ROOM_GAME_ING ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "now room not created");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		// check now turn count
		int turn_count = Integer.parseInt(turn_count_str);
		
		if( (room.getNowTurnCount() != (turn_count-1)) && (room.getNowTurnCount() != turn_count)  ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "turn count error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
				
		// check master or slave
		if( room.getSeqNumMaster().equals(seq_num) ) // master
			master_flag = true;
		else // slave
			master_flag = false;
		
		// valid check client turn count : odd/even
		if( master_flag && ((room.getNowTurnCount() % 2) != 0 ) ) { // only master turn
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "my turn error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		else if( !master_flag && ((room.getNowTurnCount() % 2) == 0 ) ) { // only slave turn
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "my turn error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// valid check client turn count
		if( (room.getNowTurnCount()+1) != Integer.parseInt(action.substring(1, 4)) ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "turn error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// main start
		ret_action = parse_action( master_flag, action, user, room, j_object ); // history 는 저장되고 증가됨
		if( ret_action == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "error parse_action");
			j_object.put("now_turn_count", action.substring(1, 4)); // atomRoom.memoryRoom.now_turn_count);
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
				
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "action:" + ret_action); // action 을 넘겨 줌
		j_object.put("action", ret_action); // action 을 넘겨 줌
		j_object.put("now_turn_count", room.getNowTurnCount());
		//j_object.put("last_position", ...); // 조건에 따라 추가 됨
		//j_object.put("spy", ...); // 조건에 따라 추가 됨
		
		logger.info("send_game_action:seqNum="+seq_num+",j_object="+j_object.toString());
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}

	private String reverse_master_index( String in_str ) {
		String out_str = in_str.substring(0, 5);
		
		int from_index = reverse_index( Integer.parseInt(in_str.substring(5, 7)) - Constant.INDEX_ADD ) + Constant.INDEX_ADD;
		int to_index =  reverse_index( Integer.parseInt(in_str.substring(7, 9)) - Constant.INDEX_ADD ) + Constant.INDEX_ADD;
		
		return out_str + String.valueOf(from_index) + String.valueOf(to_index);
	}
	
	// 현재 turn_count 가 정상적인 경우 오직 기다리는 상대방 만이 호출
	private String turn_count_action( Boolean master_flag, int turn_count, Room room, JSONObject j_object ) { // 대기 상태에서 호출
		logger.info("turn_count_action:master_flag="+master_flag+",turn_count="+turn_count);
		
		int from_master_index;
		String cmd;
		String spy_str = "";
		
		if( turn_count > room.getNowTurnCount() ) // 아직 생성되지 않은 history
			return null;
		
		String ret_action = room.getHistoryArr()[ turn_count - Constant.TURN_COUNT_START ];
		if(  ret_action == null )
			return null;
		
		/* 복기 할때도 사용하기 위해 이것을 제거 함
		if( room.game_end_flag ) { // MW1073127:PO288209192173165156141137124
			ret_action = ret_action.substring(0, 9);
			ret_action += ":PO" + make_other_mal_str( master_flag, room );
			return ret_action;
		}
		*/
		
		cmd = ret_action.substring(0, 2);
		
		if( "TO".equals(cmd) || "PO".equals(cmd) )
			return ret_action;
		
		// MV FW FL MW ML
		from_master_index = Integer.parseInt( ret_action.substring(5, 7) ) - Constant.INDEX_ADD; // backup
		if( ret_action.length() > 9 ) { // MW1073127 -> MW1073127S9
			spy_str = ret_action.substring(9, 11);
			ret_action = ret_action.substring(0, 9); // default 로, spy 정보를 제거하여 보내줌...
		}
		
		if( master_flag == false )
			ret_action = reverse_master_index( ret_action ); // spy_str 이 제거되어 return 됨
		
		if( "MW".equals(cmd) || "ML".equals(cmd) ) { // game end
			j_object.put("last_position", make_other_mal_str( master_flag, room ));
			return ret_action;
		}
		
		if( "".equals(spy_str) ) // 스파이 처리가 없는 경우
			return ret_action;
		
		/*
		// if( "FW".equals(cmd) || "FL".equals(cmd) ) { // fight
		if( master_flag == false ) {				
			if("FW".equals(cmd))
				ret_action = ret_action.replace("FW", "FL");
			else
				ret_action = ret_action.replace("FL", "FW");
		}
		*/
		
		/*
		if( master_flag == true && ((turn_count % 2) == 0) ) // slave history data
			return ret_action;
		
		if( master_flag == false && ((turn_count % 2) == 1) ) // master history data
			return ret_action;
		*/
			
		// treat spy...
		if( master_flag == true ) {
			if( (spy_str.charAt(0) == 'M') || spy_str.charAt(0) == 'B' ) { // slave history data - 상대가 나를 공격
				j_object.put("spy", String.valueOf(from_master_index + Constant.INDEX_ADD) + spy_str.substring(1)); // String.valueOf(Math.abs(room_pan[from_master_index])) );				
			}
			/*
			if( (turn_count % 2) == 1 ) { // master history data 자신의 action
				if( room_pan[from_master_index] == Constant.MalType.MAL_SPY )
					j_object.put("spy", String.valueOf(to_master_index + Constant.INDEX_ADD) + spy_str); // String.valueOf(Math.abs(room_pan[to_master_index])) );				
			}
			else { // slave history data - 상대가 나를 공격
				if( room_pan[to_master_index] == Constant.MalType.MAL_SPY )
					j_object.put("spy", String.valueOf(from_master_index + Constant.INDEX_ADD) + spy_str); // String.valueOf(Math.abs(room_pan[from_master_index])) );				
			}
			*/
		}
		else { // slave
			if( (spy_str.charAt(0) == 'S') || spy_str.charAt(0) == 'B' ) { // slave history data - 상대가 나를 공격
				j_object.put("spy", String.valueOf(reverse_index(from_master_index) + Constant.INDEX_ADD) + spy_str.substring(1)); // String.valueOf(Math.abs(room_pan[from_master_index])) );				
			}
			/*
			if( (turn_count % 2) == 0 ) { // slave history data - 자신의 action
				if( room_pan[from_master_index] == -Constant.MalType.MAL_SPY )
					j_object.put("spy", String.valueOf(reverse_index(to_master_index) + Constant.INDEX_ADD) + spy_str); // String.valueOf(Math.abs(room_pan[to_master_index])) );				
			}
			else { // master history data - 상대가 나를 공격
				if( room_pan[to_master_index] == -Constant.MalType.MAL_SPY )
					j_object.put("spy", String.valueOf(reverse_index(from_master_index) + Constant.INDEX_ADD) + spy_str); // String.valueOf(Math.abs(room_pan[from_master_index])) );				
			}
			*/
		}
		
		return ret_action;
	}
	
	// only waiting person call, 현재의 turn_count 가 합당 해야됨
	@RequestMapping(params = "check_game_action") // called by AJAX
	public void check_game_action(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num,
			@RequestParam(value = "turnCount") String turn_count_str, HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		logger.info("check_game_action:seqNum="+seq_num+",turnCount="+turn_count_str);
		
		Boolean master_flag;
		String ret_action;
		int turn_count;
		
		JSONObject j_object = new JSONObject();
		
		// 기본 check start
		if( (PreventInjection.ValidSeqNum(seq_num) == false) || PreventInjection.ValidTurnCount(turn_count_str) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "check action error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정

		// Room
		if( user.getTournamentKey() == null || user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		// 
		if( (room.getRoomStatus() != RoomStatus.STATUS_ROOM_GAME_ING) && (room.getRoomStatus() != RoomStatus.STATUS_ROOM_GAME_ENDED) ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "now room not created");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		// check now turn count
		turn_count = Integer.parseInt(turn_count_str);
		
		if( (room.getNowTurnCount() != (turn_count-1)) && (room.getNowTurnCount() != turn_count)  ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "turn count error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
				
		// check master or slave
		if( room.getSeqNumMaster().equals(seq_num) ) // master
			master_flag = true;
		else // slave
			master_flag = false;

		// valid check client turn count : odd/even
		/* 초기 호출시 문제 발생하여 코멘트 함
		if( master_flag && ((room.getNowTurnCount() % 2) == 0 ) ) { // only master turn
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "my turn error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		else if( !master_flag && ((room.getNowTurnCount() % 2) != 0 ) ) { // only slave turn
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "my turn error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		*/
		
		// main start
		if ( room.getRoomStatus() == RoomStatus.STATUS_ROOM_GAME_ENDED ) {
			user.setGameUserStatus(GameUserStatus.STATUS_USER_NONE);
			user.setRoomKey(null);
			user.setTournamentKey(null);
		}
		
		ret_action = turn_count_action( master_flag, turn_count, room, j_object ); // 대기 상태에서 호출
		if( ret_action == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "no history");
			j_object.put("now_turn_count", room.getNowTurnCount());
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
				
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "action:" + ret_action); // action 을 넘겨 줌
		j_object.put("action", ret_action); // action 을 넘겨 줌
		j_object.put("now_turn_count", turn_count_str); // atomRoom.memoryRoom.now_turn_count);
		//j_object.put("last_position", ...); // 조건에 따라 추가 됨
		//j_object.put("spy", ...); // 조건에 따라 추가 됨
		
		logger.info("check_game_action:seqNum="+seq_num+",j_object="+j_object.toString());
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}

	@RequestMapping(params = "now_game_status")
	public void now_game_status(@RequestParam(value = ConstantStr.STR_SEQ_NUM) String seq_num, HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		logger.info("now_game_status:seqNum="+seq_num);
		
		JSONObject j_object = new JSONObject();
		
		if( PreventInjection.ValidSeqNum(seq_num) == false ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "check seq_num error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		// User
        HttpSession session  =  request.getSession(false);		
		GameUser user = gameUserMap.get(seq_num);
		
		if( user == null || user.getSessionId() != session.getId() || user.getGameUserStatus() != GameUserStatus.STATUS_TOURNAMENT_JOINED ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		
		user.setUpdatedTime(System.currentTimeMillis()); // 사용자 접속 최근 시간 수정

		// Room
		if( user.getTournamentKey() == null || user.getRoomKey() == null ) {
			j_object.put(ConstantStr.STR_RT, "1"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "game user error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}

		Room room = roomMap.get(user.getRoomKey());
		if(room == null) {
			j_object.put(ConstantStr.STR_RT, "2"); // 1 보다 크면 오류...
			j_object.put(ConstantStr.STR_MSG, "get room error");
			
			response.getWriter().write(j_object.toString());
			return; // exit
		}
		// room.setUpdatedTime(System.currentTimeMillis()); // time setting
		
		j_object.put("room_updated_time", room.getUpdatedTime());
		j_object.put(ConstantStr.STR_NOW_TURN_COUNT, room.getNowTurnCount());
		
		// check master or slave
		if( room.getSeqNumMaster().equals(seq_num) ) // master master_slave
			j_object.put("master_slave", "master");
		else if( room.getSeqNumMaster().equals(seq_num) )// slave
			j_object.put("master_slave", "slave");
		else
			j_object.put("master_slave", "none");
		
		// valid check client turn count : odd/even
		if( room.getNowTurnCount() >= 102 ) {
			if( (room.getNowTurnCount() % 2) != 0 ) { // only master turn 103, 105
				j_object.put("now_turn_owner", "master");
			}
			else { // only slave turn 104, 106
				j_object.put("now_turn_owner", "slave");
			}
		}
		else
			j_object.put("now_turn_owner", "none");
		
		j_object.put(ConstantStr.STR_RT, "0"); // 1 보다 크면 오류...
		j_object.put(ConstantStr.STR_MSG, "OK"); // action 을 넘겨 줌
		
		logger.info("now_game_status:seqNum="+seq_num+",j_object="+j_object.toString());
		
		response.getWriter().write(j_object.toString());
		return; // exit
	}
}
