package com.kidmobile.csr.global;

public class MemoryUser {
	String key;
	String name;
	public String room_key;
	public Constant.GameUserStatus status;
	
	public MemoryUser(String key) {
		this.key = key;
		status = Constant.GameUserStatus.STATUS_USER_NONE;
	}
}
