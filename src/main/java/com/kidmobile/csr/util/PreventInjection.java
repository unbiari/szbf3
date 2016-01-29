package com.kidmobile.csr.util;

import java.util.regex.Pattern;

public class PreventInjection {
	
	static final String regx_user_id = "^[0-9a-zA-Z]{1,10}$";
	static final String regx_pass = "^[0-9a-zA-Z]{4,20}$";
	static final String regx_phone = "^[0-9]{11}$"; //11글자(숫자)
	static final String regx_seq_num = "^[0-9]{1,10}$"; //숫자 최대 10억명
	static final String regx_turn_count = "^[0-9]{3,3}$"; //숫자 최대 10억명
	static final String regx_action_str = "^[0-9A-Z]{4,8}$"; // M1031924, T105
	
	static public boolean ValidUserId(String id_str) {
		Pattern pattern = Pattern.compile(regx_user_id);
		
		if( pattern.matcher(id_str).find() )
			return true;
		else
			return false;
	}
	
	static public boolean ValidPass(String pass_str) {
		Pattern pattern = Pattern.compile(regx_pass);
		
		if( pattern.matcher(pass_str).find() )
			return true;
		else
			return false;
	}
	
	static public boolean ValidPhone(String phone_str) {
		Pattern pattern = Pattern.compile(regx_phone);
		
		if( pattern.matcher(phone_str).find() )
			return true;
		else
			return false;
	}
	
	static public boolean ValidSeqNum(String seq_num) {
		Pattern pattern = Pattern.compile(regx_seq_num);
		
		if( pattern.matcher(seq_num).find() )
			return true;
		else
			return false;
	}

	static public boolean ValidTurnCount(String turn_count) {
		Pattern pattern = Pattern.compile(regx_turn_count);
		
		if( pattern.matcher(turn_count).find() )
			return true;
		else
			return false;
	}

	static public boolean ValidActionStr(String action_str) {
		Pattern pattern = Pattern.compile(regx_action_str);
		
		if( pattern.matcher(action_str).find() )
			return true;
		else
			return false;
	}
}
