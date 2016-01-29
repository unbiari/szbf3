package com.kidmobile.csr.global;

import com.kidmobile.csr.global.Constant.GameUserStatus;

public class GameUser {
	private String seqNum;
	private String sessionId;
	private String userName;
	
	private String tournamentKey;
	private String roomKey;
	
	private GameUserStatus userStatus;
	private long startedTime;
	private long updatedTime;
	
	// my functions...
	
	public GameUser(String seqNum, String sessionId) {
		this.seqNum = seqNum;
		this.sessionId = sessionId;
		this.userName = null;
		this.tournamentKey = null;
		this.roomKey = null;
		this.userStatus = GameUserStatus.STATUS_USER_NONE;
		this.startedTime = System.currentTimeMillis();
		this.updatedTime = this.startedTime;
	}
	
	// getter/setter
	
	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTournamentKey() {
		return tournamentKey;
	}

	public void setTournamentKey(String tournamentKey) {
		this.tournamentKey = tournamentKey;
	}

	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public Constant.GameUserStatus getGameUserStatus() {
		return userStatus;
	}

	public void setGameUserStatus(Constant.GameUserStatus userStatus) {
		this.userStatus = userStatus;
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
