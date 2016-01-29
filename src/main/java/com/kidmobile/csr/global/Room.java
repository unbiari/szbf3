package com.kidmobile.csr.global;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kidmobile.csr.global.Constant.RoomStatus;

public class Room {
	private String roomKey;
	private String roomName;
	
	private String seqNumMaster;
	private String seqNumSlave;
	
	private int nowTurnCount; // (100 +) 0:init, 1:replace, 2:replace, 3:master_action, 4:slave_action...
	private byte[] roomPan = new byte[ Constant.PAN_SIZE + 1 ]; // 0 is no use... 1~24
	private String[] historyArr = new String[Constant.MAX_TURN_COUNT + 2]; // 2 is position...
	
	private RoomStatus roomStatus;
	private long startedTime;
	private long updatedTime;
	
	// my functions...
	
	public Room() {
		this.roomStatus = RoomStatus.STATUS_ROOM_NONE;
	}
	
	public void Initialize( String key ) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String formattedDate = dateFormat.format(date);
		
		this.roomKey = key;
		this.roomName = formattedDate + key;
		this.seqNumMaster = key;
		this.seqNumSlave = null;
		this.nowTurnCount = Constant.TURN_COUNT_START;
		this.roomStatus = RoomStatus.STATUS_ROOM_INITIALIZED;
		this.startedTime = System.currentTimeMillis();
		this.updatedTime = this.startedTime;
	}

	// getter/setter
	
	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getSeqNumMaster() {
		return seqNumMaster;
	}

	public void setSeqNumMaster(String seqNumMaster) {
		this.seqNumMaster = seqNumMaster;
	}

	public String getSeqNumSlave() {
		return seqNumSlave;
	}

	public void setSeqNumSlave(String seqNumSlave) {
		this.seqNumSlave = seqNumSlave;
	}

	public int getNowTurnCount() {
		return nowTurnCount;
	}

	public void setNowTurnCount(int nowTurnCount) {
		this.nowTurnCount = nowTurnCount;
	}

	public byte[] getRoomPan() {
		return roomPan;
	}

	public void setRoomPan(byte[] roomPan) {
		this.roomPan = roomPan;
	}

	public String[] getHistoryArr() {
		return historyArr;
	}

	public void setHistoryArr(String[] historyArr) {
		this.historyArr = historyArr;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	public long getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
	}

	public long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

}
