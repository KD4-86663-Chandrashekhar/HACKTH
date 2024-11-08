package com.movies.Daos;


import java.util.List;

import com.entity.User;


public interface UserDao extends AutoCloseable{

	List<User> findAll() throws Exception;
	User findById(int id) throws Exception;
	int save(User u) throws Exception;
	User findByEmail(String email) throws Exception;
	int update(User u) throws Exception;
	int updatePassword(User u) throws Exception;
	
	

}