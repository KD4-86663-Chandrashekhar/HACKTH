package com.movies.Daos;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.User;




public class UserDaoImpl extends Dao implements UserDao {
 
	
	private PreparedStatement stmtFindAll;
	private PreparedStatement stmtFindById;
	private PreparedStatement stmtFindByEmail;
	private PreparedStatement stmtSave;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtUpdatePassword;
	
	
	public UserDaoImpl() throws Exception{
		 String sqlFindAll="SELECT * FROM users;";
		 stmtFindAll =con.prepareStatement(sqlFindAll);
		 String sqlFindById="SELECT * FROM users where id=?;";
		 stmtFindById =con.prepareStatement(sqlFindById);
		 String sqlFindByEmail = "SELECT * FROM users WHERE email=?";
		 stmtFindByEmail = con.prepareStatement(sqlFindByEmail);
		 String sqlSave = "INSERT INTO users (first_name, last_name, email,mobile,birth,password) VALUES(?, ?, ?, ?, ?, ?)";
		 stmtSave = con.prepareStatement(sqlSave);
		 String sqlUpdate = "UPDATE USERS SET first_name=?, last_name=?, email=?,mobile=?,birth=?,password=? WHERE id=?";
		 stmtUpdate = con.prepareStatement(sqlUpdate);
		 String sqlUpdatePassword = "UPDATE USERS SET password=? WHERE id=?";
		 stmtUpdatePassword = con.prepareStatement(sqlUpdatePassword);
		

	
	}
	

	@Override
	public void close() throws Exception {
		stmtSave.close();
		stmtFindByEmail.close();
		stmtFindById.close();
		stmtFindAll.close();
		super.close();
	}

	@Override
	public List<User> findAll() throws Exception 
	{
		List<User> list= new ArrayList<User>();
		try(ResultSet rst=stmtFindAll.executeQuery())
		{
			while(rst.next())
			{
				int id = rst.getInt("id");
				String firstName = rst.getString("first_name");
				String lastName = rst.getString("last_name");
				String email = rst.getString("email");
				String mobile = rst.getString("mobile");
				Date birth = rst.getDate("birth");
				String password = rst.getString("password");
				User u = new User(id, firstName, lastName, email, mobile, birth, password);
				list.add(u);
				
			}
		}
		
		return list;
	}


	@Override
	public User findById(int id) throws Exception {
		stmtFindById.setInt(1, id);
		try(ResultSet rst=stmtFindById.executeQuery())
		{
			if(rst.next())
			{
				id = rst.getInt("id");
				String firstName = rst.getString("first_name");
				String lastName = rst.getString("last_name");
				String email = rst.getString("email");
				String mobile = rst.getString("mobile");
				Date birth = rst.getDate("birth");
				String password = rst.getString("password");
				User u = new User(id, firstName, lastName, email, mobile, birth, password);
		        return u;
			    
				
			}
		}
		
		
		return null;
	}

	@Override
	public User findByEmail(String email) throws Exception {
		stmtFindByEmail.setString(1, email);
		try(ResultSet rst = stmtFindByEmail.executeQuery()) {
			if(rst.next()) {
				int id = rst.getInt("id");
				String firstName = rst.getString("first_name");
				String lastName = rst.getString("last_name");
				 email = rst.getString("email");
				String mobile = rst.getString("mobile");
				Date birth = rst.getDate("birth");
				String password = rst.getString("password");
				User u = new User(id, firstName, lastName, email, mobile, birth, password);
		        return u;
			}
		} 
		return null;
	}


	
	@Override
	public int save(User u) throws Exception {
		stmtSave.setString(1, u.getFirstName());
		stmtSave.setString(2, u.getLastName());
		stmtSave.setString(3, u.getEmail());
		stmtSave.setString(4, u.getMobile());
		stmtSave.setDate(5, u.getBirth());
		stmtSave.setString(6, u.getPassword());
		int count = stmtSave.executeUpdate();
		return count;
	}


	@Override
	public int update(User u) throws Exception {
	
		stmtUpdate.setString(1, u.getFirstName());
		stmtUpdate.setString(2, u.getLastName());
		stmtUpdate.setString(3, u.getEmail());
		stmtUpdate.setString(4, u.getMobile());
		stmtUpdate.setDate(5, u.getBirth());
		stmtUpdate.setString(6, u.getPassword());
		stmtUpdate.setInt(7, u.getId());
		int count = stmtUpdate.executeUpdate();
		return count;
	 
	}


	@Override
	public int updatePassword(User u) throws Exception {
		stmtUpdatePassword.setString(1, u.getPassword());
		stmtUpdatePassword.setInt(2, u.getId());
		int count = stmtUpdatePassword.executeUpdate();
		return count;
	}


	


}


	


	

 
