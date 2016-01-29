package com.kidmobile.csr.game_tbl;

import java.util.List;

public interface Game_TblService {
	public List<Game_Tbl> getLists(int page, int rows);  
	public int getTotalRecords();
	public void create(Game_Tbl record);
	public void update(Game_Tbl record);
	public void remove(int id);
	
	public List<Game_Tbl> getRecentLists(int size);  
	public boolean existSearchLists(Game_Tbl record);  
}
