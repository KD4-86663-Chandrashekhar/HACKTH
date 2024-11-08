package com.movies.Daos;

import java.sql.PreparedStatement;

import com.entity.Shares;

public interface ShareDao extends AutoCloseable 
{
	int save(Shares s) throws Exception;
	int delete(int id)throws Exception;
	
}
