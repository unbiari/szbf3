package com.kidmobile.csr.game_tbl;

import java.io.Serializable;
import java.util.Date;

public class Game_Tbl implements Serializable {
	private static final long serialVersionUID = 1L;

	private int start_Record; // no in database
	private int page_Limit; // no in database

	private int game_SeqNum;
	private String room_Name;
	private int count_Grade;
	private float avg_Grade;
	private String master_ID;
	private String guest_ID;
	private String master_WinFlag;
	private String master_Country;
	private String guest_Country;
	private int master_GetGold;
	private int guest_GetGold;
	private int game_TimeLength;
	private int game_TurnTotal;
	private int winCount_Max;
	private int winCount_Now;
	private String game_History;
	
	private Date insert_Time;
	private Date last_Time;
	
	// getter, setter
	public int getStart_Record() {
		return start_Record;
	}
	public void setStart_Record(int start_Record) {
		this.start_Record = start_Record;
	}
	public int getPage_Limit() {
		return page_Limit;
	}
	public void setPage_Limit(int page_Limit) {
		this.page_Limit = page_Limit;
	}
	public int getGame_SeqNum() {
		return game_SeqNum;
	}
	public void setGame_SeqNum(int game_SeqNum) {
		this.game_SeqNum = game_SeqNum;
	}
	public String getRoom_Name() {
		return room_Name;
	}
	public void setRoom_Name(String room_Name) {
		this.room_Name = room_Name;
	}
	public int getCount_Grade() {
		return count_Grade;
	}
	public void setCount_Grade(int count_Grade) {
		this.count_Grade = count_Grade;
	}
	public float getAvg_Grade() {
		return avg_Grade;
	}
	public void setAvg_Grade(float avg_Grade) {
		this.avg_Grade = avg_Grade;
	}
	public String getMaster_ID() {
		return master_ID;
	}
	public void setMaster_ID(String master_ID) {
		this.master_ID = master_ID;
	}
	public String getGuest_ID() {
		return guest_ID;
	}
	public void setGuest_ID(String guest_ID) {
		this.guest_ID = guest_ID;
	}
	public String getMaster_WinFlag() {
		return master_WinFlag;
	}
	public void setMaster_WinFlag(String master_WinFlag) {
		this.master_WinFlag = master_WinFlag;
	}
	public String getMaster_Country() {
		return master_Country;
	}
	public void setMaster_Country(String master_Country) {
		this.master_Country = master_Country;
	}
	public String getGuest_Country() {
		return guest_Country;
	}
	public void setGuest_Country(String guest_Country) {
		this.guest_Country = guest_Country;
	}
	public int getMaster_GetGold() {
		return master_GetGold;
	}
	public void setMaster_GetGold(int master_GetGold) {
		this.master_GetGold = master_GetGold;
	}
	public int getGuest_GetGold() {
		return guest_GetGold;
	}
	public void setGuest_GetGold(int guest_GetGold) {
		this.guest_GetGold = guest_GetGold;
	}
	public int getGame_TimeLength() {
		return game_TimeLength;
	}
	public void setGame_TimeLength(int game_TimeLength) {
		this.game_TimeLength = game_TimeLength;
	}
	public int getGame_TurnTotal() {
		return game_TurnTotal;
	}
	public void setGame_TurnTotal(int game_TurnTotal) {
		this.game_TurnTotal = game_TurnTotal;
	}
	public int getWinCount_Max() {
		return winCount_Max;
	}
	public void setWinCount_Max(int winCount_Max) {
		this.winCount_Max = winCount_Max;
	}
	public int getWinCount_Now() {
		return winCount_Now;
	}
	public void setWinCount_Now(int winCount_Now) {
		this.winCount_Now = winCount_Now;
	}
	public String getGame_History() {
		return game_History;
	}
	public void setGame_History(String game_History) {
		this.game_History = game_History;
	}
	public Date getInsert_Time() {
		return insert_Time;
	}
	public void setInsert_Time(Date insert_Time) {
		this.insert_Time = insert_Time;
	}
	public Date getLast_Time() {
		return last_Time;
	}
	public void setLast_Time(Date last_Time) {
		this.last_Time = last_Time;
	}
}
