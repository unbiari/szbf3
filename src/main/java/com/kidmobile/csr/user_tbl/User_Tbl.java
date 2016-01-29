package com.kidmobile.csr.user_tbl;

import java.io.Serializable;
import java.util.Date;

public class User_Tbl implements Serializable {
	private static final long serialVersionUID = 1L;

	private int start_Record; // no in database
	private int page_Limit; // no in database
	
	private int user_SeqNum;
	private String user_ID;
	private String user_PW;
	private int user_Level;
	private String user_Country;
	private String user_Email;
	private String user_TelNum;
	private int count_Total;
	private int count_Win;
	private int count_Loss;
	private int count_MaxWin;
	private int count_NowWin;
	private int count_Over;
	private int gold_Total;
	private int gold_GetLast;	
	private String tournament_Flag;	
	
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
	public int getUser_SeqNum() {
		return user_SeqNum;
	}
	public void setUser_SeqNum(int user_SeqNum) {
		this.user_SeqNum = user_SeqNum;
	}
	public String getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}
	public String getUser_PW() {
		return user_PW;
	}
	public void setUser_PW(String user_PW) {
		this.user_PW = user_PW;
	}
	public int getUser_Level() {
		return user_Level;
	}
	public void setUser_Level(int user_Level) {
		this.user_Level = user_Level;
	}
	public String getUser_Country() {
		return user_Country;
	}
	public void setUser_Country(String user_Country) {
		this.user_Country = user_Country;
	}
	public String getUser_Email() {
		return user_Email;
	}
	public void setUser_Email(String user_Email) {
		this.user_Email = user_Email;
	}
	public String getUser_TelNum() {
		return user_TelNum;
	}
	public void setUser_TelNum(String user_TelNum) {
		this.user_TelNum = user_TelNum;
	}
	public int getCount_Total() {
		return count_Total;
	}
	public void setCount_Total(int count_Total) {
		this.count_Total = count_Total;
	}
	public int getCount_Win() {
		return count_Win;
	}
	public void setCount_Win(int count_Win) {
		this.count_Win = count_Win;
	}
	public int getCount_Loss() {
		return count_Loss;
	}
	public void setCount_Loss(int count_Loss) {
		this.count_Loss = count_Loss;
	}
	public int getCount_MaxWin() {
		return count_MaxWin;
	}
	public void setCount_MaxWin(int count_MaxWin) {
		this.count_MaxWin = count_MaxWin;
	}
	public int getCount_NowWin() {
		return count_NowWin;
	}
	public void setCount_NowWin(int count_NowWin) {
		this.count_NowWin = count_NowWin;
	}
	public int getCount_Over() {
		return count_Over;
	}
	public void setCount_Over(int count_Over) {
		this.count_Over = count_Over;
	}
	public int getGold_Total() {
		return gold_Total;
	}
	public void setGold_Total(int gold_Total) {
		this.gold_Total = gold_Total;
	}
	public int getGold_GetLast() {
		return gold_GetLast;
	}
	public void setGold_GetLast(int gold_GetLast) {
		this.gold_GetLast = gold_GetLast;
	}
	public String getTournament_Flag() {
		return tournament_Flag;
	}
	public void setTournament_Flag(String tournament_Flag) {
		this.tournament_Flag = tournament_Flag;
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
