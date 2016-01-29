package com.kidmobile.csr.user_tbl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

@Service("User_TblService")
public class User_TblServiceImpl implements User_TblService {
	
	@Inject
	@Named("User_TblDao")
	private User_TblDao dao;
	
	public List<User_Tbl> getLists(int page, int rows)
	{
		return dao.getLists(page, rows);
	}
	
	public int getTotalRecords()
	{
		return dao.getTotalRecords();
	}
	
	public void create(User_Tbl record) {
		dao.create(record);
	}
	
	public void update(User_Tbl record){
		dao.update(record);
	}
	
	public void remove(int id){
		dao.remove(id);
	}


	public int checkUserID(String user_id){
		return dao.checkUserID(user_id);
	}
	public User_Tbl getUserInfo(String user_id){
		return dao.getUserInfo(user_id);
	}
}
