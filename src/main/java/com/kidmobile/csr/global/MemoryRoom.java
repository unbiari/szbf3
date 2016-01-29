package com.kidmobile.csr.global;

public class MemoryRoom {
	String key;
	String name;
	
	public String key_master;
	public String key_slave;
	public int now_turn_count; // (100 +) 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
	public Boolean game_end_flag; // request qame end and positions
	
	public byte[] pan = new byte[ Constant.PAN_SIZE + 1 ]; // 0 is no use... 1~24
	
	public String[] history_arr = new String[Constant.MAX_TURN_COUNT + 2]; // 2 is position...
	
	public int[] big_data_m = new int[Constant.MAX_BIG_DATA];
	public int[] big_data_s = new int[Constant.MAX_BIG_DATA];
	
	public MemoryRoom( String key ) {
		int i;
		this.key = key;
		
		// data 초기화
		for( i=0; i<Constant.MAX_BIG_DATA; i++ ) {
			big_data_m[i] = 0;
			big_data_s[i] = 0;
		}
	}

}
