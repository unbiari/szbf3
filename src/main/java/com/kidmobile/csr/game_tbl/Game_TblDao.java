package com.kidmobile.csr.game_tbl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("Game_TblDao")
public class Game_TblDao extends SqlSessionDaoSupport {
	public List<Game_Tbl> getLists(int page, int rows) {
		Game_Tbl item = new Game_Tbl();
		
		item.setStart_Record((page-1)*rows); // set start record
		item.setPage_Limit(rows); // set page limit
		
		return getSqlSession().selectList("Game_Tbl.listWithPage", item);
	}
	
	public int getTotalRecords(){
		int ret_value = getSqlSession().selectOne("Game_Tbl.getTotalRecords");
		
		return ret_value;
	}
	
	public void create(Game_Tbl record){
		getSqlSession().insert("Game_Tbl.insert", record);
		
		return;
	}
	
	public void update(Game_Tbl record){
		getSqlSession().update("Game_Tbl.update", record);
		
		return;
	}
	
	public void remove(int id){
		getSqlSession().delete("Game_Tbl.delete", id);
		
		return;
	}


	public List<Game_Tbl> getRecentLists(int size) {
		Game_Tbl item = new Game_Tbl();
		
		item.setPage_Limit(size); // set page limit
		
		return getSqlSession().selectList("Game_Tbl.listRecent", item);
	}

	public boolean existSearchLists(Game_Tbl item) {
		if( getSqlSession().selectList("Game_Tbl.SearchLists", item) == null )
			return true;
		return false;
	}
}
