package com.kidmobile.csr.global;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.kidmobile.csr.global.Constant.TournamentStatus;

public class Tournament {
	private String tournamentKey;
	private String tournamentName;
	
	private int beginWinCount;
	private int endWinCount;
	
	private long expiredDateTime; // 0 : 무한대
	
	private int maxUserCount;
	private int nowUserCount;
	
	private TournamentStatus touramentStatus;
	private long startedTime;
	private long updatedTime;
	
	private ArrayList<String> roomKeyList = new ArrayList<String>(); // room key list 
	private Room nowRoom0 = null;
	private Room nowRoom1 = null;
	private Room nowRoom2 = null;
	private Room nowRoomx = null;
	
	// my functions...
	
	public Tournament() {
		this.touramentStatus = TournamentStatus.STATUS_TOURNAMENT_NONE;
	}
	
	public void Initialize(String key, int beginWinCount, int endWinCount) {
		this.tournamentKey = key;
		this.tournamentName = null;
		this.beginWinCount = beginWinCount;
		this.endWinCount = endWinCount; // 1, 2, 3
		this.expiredDateTime = 0L;
		//this.maxUserCount = 2^endWinCount;
		this.nowUserCount = 0; // 초기화 때는 0 으로 설정
		this.touramentStatus = TournamentStatus.STATUS_TOURNAMENT_INITIALIZED;
		this.startedTime = System.currentTimeMillis();
		this.updatedTime = this.startedTime;
		
		switch(endWinCount) {
		case 1:
			nowRoom0 = new Room(); // 첫 게임 시작
			this.maxUserCount = 2;
			break;
		case 2:
			nowRoom0 = new Room(); // 첫 게임 시작
			nowRoom1 = new Room(); // 1승후 게임 시작
			this.maxUserCount = 4;
			break;
		case 3:
			nowRoom0 = new Room(); // 첫 게임 시작
			nowRoom1 = new Room(); // 1승후 게임 시작
			nowRoom2 = new Room(); // 2연승후 게임 시
			this.maxUserCount = 8;
			break;
		default: // case x : 소규모 게임 대회 - 시간별로 설정
			nowRoomx = new Room(); // 첫 게임 시작
			this.maxUserCount = 100000;
			break;
		}
	}
	
	// getter/setter
	
	public String getTournamentKey() {
		return tournamentKey;
	}
	public void setTournamentKey(String tournamentKey) {
		this.tournamentKey = tournamentKey;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public int getBeginWinCount() {
		return beginWinCount;
	}
	public void setBeginWinCount(int beginWinCount) {
		this.beginWinCount = beginWinCount;
	}
	public int getEndWinCount() {
		return endWinCount;
	}
	public void setEndWinCount(int endWinCount) {
		this.endWinCount = endWinCount;
	}
	public long getExpiredDateTime() {
		return expiredDateTime;
	}
	public void setExpiredDateTime(long expiredDateTime) {
		this.expiredDateTime = expiredDateTime;
	}
	public int getMaxUserCount() {
		return maxUserCount;
	}
	public void setMaxUserCount(int maxUserCount) {
		this.maxUserCount = maxUserCount;
	}
	public int getNowUserCount() {
		return nowUserCount;
	}
	public void setNowUserCount(int nowUserCount) {
		this.nowUserCount = nowUserCount;
	}
	public Constant.TournamentStatus getTouramentStatus() {
		return touramentStatus;
	}
	public void setTouramentStatus(Constant.TournamentStatus touramentStatus) {
		this.touramentStatus = touramentStatus;
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
	public ArrayList<String> getRoomKeyList() {
		return roomKeyList;
	}
	public void setRoomKeyList(ArrayList<String> roomKeyList) {
		this.roomKeyList = roomKeyList;
	}

	public Room getNowRoom0() {
		return nowRoom0;
	}

	public void setNowRoom0(Room nowRoom0) {
		this.nowRoom0 = nowRoom0;
	}

	public Room getNowRoom1() {
		return nowRoom1;
	}

	public void setNowRoom1(Room nowRoom1) {
		this.nowRoom1 = nowRoom1;
	}

	public Room getNowRoom2() {
		return nowRoom2;
	}

	public void setNowRoom2(Room nowRoom2) {
		this.nowRoom2 = nowRoom2;
	}

	public Room getNowRoomx() {
		return nowRoomx;
	}

	public void setNowRoomx(Room nowRoomx) {
		this.nowRoomx = nowRoomx;
	}
}
