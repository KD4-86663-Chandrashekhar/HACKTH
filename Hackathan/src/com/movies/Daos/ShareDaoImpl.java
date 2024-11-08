package com.movies.Daos;

import java.sql.PreparedStatement;

import com.entity.Shares;

public class ShareDaoImpl extends Dao implements ShareDao {
  private PreparedStatement stmtSave; 
  private PreparedStatement stmtDelete;

  
  public ShareDaoImpl() throws Exception {
		 String sqlSave = "INSERT INTO shares VALUES(?, ?)";
		 stmtSave = con.prepareStatement(sqlSave);
		 String sqldelete = "delete from reviews where review_id=?";
		 stmtDelete=con.prepareStatement(sqldelete);
		
	}

	@Override
	public int save(Shares s) throws Exception {
		stmtSave.setInt(1,s.getReview_id());
		stmtSave.setInt(2,s.getUser_id());
		int count=stmtSave.executeUpdate();
		return count;
	}

	@Override
	public int delete(int id) throws Exception {
		stmtDelete.setInt(1,id);
		int count=stmtDelete.executeUpdate();
		return count;
	}

}
