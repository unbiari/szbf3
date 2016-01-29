package com.kidmobile.csr.user_tbl;

import java.util.List;

public interface User_TblService {
	public List<User_Tbl> getLists(int page, int rows);  
	public int getTotalRecords();
	public void create(User_Tbl record);
	public void update(User_Tbl record);
	public void remove(int id);
	
	public int checkUserID(String user_id);
	public User_Tbl getUserInfo(String user_id);
}
