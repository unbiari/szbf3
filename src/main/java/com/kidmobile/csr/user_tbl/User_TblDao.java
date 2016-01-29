package com.kidmobile.csr.user_tbl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("User_TblDao")
public class User_TblDao extends SqlSessionDaoSupport {
	public List<User_Tbl> getLists(int page, int rows) {
		User_Tbl item = new User_Tbl();
		
		item.setStart_Record((page-1)*rows); // set start record
		item.setPage_Limit(rows); // set page limit
		
		return getSqlSession().selectList("User_Tbl.listWithPage", item);
	}
	
	public int getTotalRecords(){
		int ret_value = getSqlSession().selectOne("User_Tbl.getTotalRecords");
		
		return ret_value;
	}
	
	public void create(User_Tbl record){
		getSqlSession().insert("User_Tbl.insert", record);
		
		return;
	}
	
	public void update(User_Tbl record){
		getSqlSession().update("User_Tbl.update", record);
		
		return;
	}
	
	public void remove(int id){
		getSqlSession().delete("User_Tbl.delete", id);
		
		return;
	}
	
	public int checkUserID( String user_id ){
		int ret_value = getSqlSession().selectOne("User_Tbl.checkUserID", user_id);
		
		return ret_value;
	}
	
	public User_Tbl getUserInfo( String user_id ){
		return getSqlSession().selectOne("User_Tbl.getUserInfo", user_id);
	}
}
