package com.kidmobile.csr.util;

import com.kidmobile.csr.global.Constant;
import com.kidmobile.csr.global.MemoryRoom;

public class BigDataTreat {
	static public boolean from_position( String key, MemoryRoom room, String history_str ) {
		if( room.key_master.equals(key) ) { // master
			//
			if( history_str.indexOf(Constant.MalType.MAL_SPY) < 5 )
				room.big_data_m[ Constant.BigData.SPY_FIRST_LINE_FLAG ] = 1;
			else
				room.big_data_m[ Constant.BigData.SPY_FIRST_LINE_FLAG ] = 0;
			//
			if( history_str.indexOf(Constant.MalType.MAL_MINE) < 5 )
				room.big_data_m[ Constant.BigData.MINE_FIRST_LINE_FLAG ] = 1;
			else
				room.big_data_m[ Constant.BigData.MINE_FIRST_LINE_FLAG ] = 0;
			//
			if( history_str.indexOf(Constant.MalType.MAL_MINE) < 5 )
				room.big_data_m[ Constant.BigData.MINE_FIRST_LINE_FLAG ] = 1;
			else
				room.big_data_m[ Constant.BigData.MINE_FIRST_LINE_FLAG ] = 0;
			// 
			room.big_data_m[ Constant.BigData.FIRST_LINE_FIRE_VALUE ] = history_str.charAt(0) + history_str.charAt(1) + history_str.charAt(2) + history_str.charAt(3) + history_str.charAt(4) - '0'*4;
			//public static final byte KING_LEFT_FLAG = 11;
			//public static final byte LEFT_FIRE_VALUE = 16;
			//public static final byte RIGHT_FIRE_VALUE = 17;
		}
		return true; 
	};
}
