package com.kidmobile.csr.global;

import java.util.HashMap;

public class AtomRoomArray {
	final static int ATOM_ROOM_ARRAY_MAX = AtomUserArray.ATOM_USER_ARRAY_MAX/2;

	AtomRoom[] atomRoom_array;
	int atomRoom_alloc_count;
	HashMap<String, Integer> atomRoom_hash;
	
	public void Init() {
		atomRoom_array =  new AtomRoom[ATOM_ROOM_ARRAY_MAX];
		atomRoom_alloc_count = 0;
		atomRoom_hash = new HashMap<String, Integer>();
		
		for( int i=0; i<ATOM_ROOM_ARRAY_MAX; i++ ) {
			atomRoom_array[i] = new AtomRoom();
			atomRoom_array[i].Reset_access_time();
		}
	}
	
	private int get_new_atomRoom_index() {
		int i;
		for( i=0; i<ATOM_ROOM_ARRAY_MAX; i++ )
			if( atomRoom_array[i].last_access_time == 0L )
				return i;
		return -1; // error
	}
	
	public AtomRoom Find_and_new_atomRoom(String key) {
		Integer I = (Integer)atomRoom_hash.get(key);
		if( I != null )
			return atomRoom_array[I];

		int i = get_new_atomRoom_index();
		if( i == -1 )
			return null;
		
		atomRoom_hash.put(key, i);
		atomRoom_alloc_count++;
		
		atomRoom_array[i].Update_access_time();
		atomRoom_array[i].memoryRoom = new MemoryRoom(key);
		
		return atomRoom_array[i];
	}

	public void Delete_atomRoom(String key) {
		Integer I = (Integer)atomRoom_hash.get(key);
		atomRoom_hash.remove(key);
		atomRoom_alloc_count--;
		
		atomRoom_array[I].Reset_access_time();
		atomRoom_array[I].memoryRoom = null;
	}

}
