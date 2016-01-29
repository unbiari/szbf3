package com.kidmobile.csr.game_tbl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

@Service("Game_TblService")
public class Game_TblServiceImpl implements Game_TblService {
	
	@Inject
	@Named("Game_TblDao")
	private Game_TblDao dao;
	
	public List<Game_Tbl> getLists(int page, int rows)
	{
		return dao.getLists(page, rows);
	}
	
	public int getTotalRecords()
	{
		return dao.getTotalRecords();
	}
	
	public void create(Game_Tbl record) {
		dao.create(record);
	}
	
	public void update(Game_Tbl record){
		dao.update(record);
	}
	
	public void remove(int id){
		dao.remove(id);
	}


	public List<Game_Tbl> getRecentLists(int size)
	{
		return dao.getRecentLists(size);
	}
	public boolean existSearchLists(Game_Tbl record)
	{
		return dao.existSearchLists(record);
	}
}
