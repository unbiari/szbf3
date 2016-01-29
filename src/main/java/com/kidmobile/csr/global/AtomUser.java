package com.kidmobile.csr.global;

public class AtomUser {
	public long last_access_time;
	public MemoryUser memoryUser;
	
	public void Update_access_time() {
		last_access_time = System.currentTimeMillis();
	}

	public void Reset_access_time() {
		this.last_access_time = 0L;
	}
}
