package com.kidmobile.csr.global;

public class AtomRoom {
	public long last_access_time;
	public MemoryRoom memoryRoom;
	
	public void Update_access_time() {
		last_access_time = System.currentTimeMillis();
	}

	public void Reset_access_time() {
		this.last_access_time = 0L;
	}
}
