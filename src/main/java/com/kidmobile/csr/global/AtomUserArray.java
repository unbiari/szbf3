package com.kidmobile.csr.global;

import java.util.HashMap;

public class AtomUserArray {
	final static int ATOM_USER_ARRAY_MAX = 1000;

	AtomUser[] atomUser_array;
	int atomUser_alloc_count;
	HashMap<String, Integer> atomUser_hash;
	
	public void init() {
		atomUser_array =  new AtomUser[ATOM_USER_ARRAY_MAX];
		atomUser_alloc_count = 0;
		atomUser_hash = new HashMap<String, Integer>();
		
		for( int i=0; i<ATOM_USER_ARRAY_MAX; i++ ) {
			atomUser_array[i] = new AtomUser();
			atomUser_array[i].Reset_access_time();
		}
	}
	
	public int get_new_atomUser_index() {
		int i;
		for( i=0; i<ATOM_USER_ARRAY_MAX; i++ )
			if( atomUser_array[i].last_access_time == 0L )
				return i;
		return -1; // error
	}
	
	public AtomUser find_and_new_atomUser(String key) {
		Integer I = (Integer)atomUser_hash.get(key);
		if( I != null )
			return atomUser_array[I];

		int i = get_new_atomUser_index();
		if( i == -1 )
			return null;
		
		atomUser_hash.put(key, i);
		atomUser_alloc_count++;
		
		atomUser_array[i].Update_access_time();
		atomUser_array[i].memoryUser = new MemoryUser(key);
		
		return atomUser_array[i];
	}

	public void delete_atomUser(String key) {
		Integer I = (Integer)atomUser_hash.get(key);
		atomUser_hash.remove(key);
		atomUser_alloc_count--;
		
		atomUser_array[I].Reset_access_time();
		atomUser_array[I].memoryUser = null;
	}

	public AtomUser find_atomUser(String key) {
		Integer I = (Integer)atomUser_hash.get(key);
		if( I != null )
			return atomUser_array[I];

		return null;
	}

}
