package com.movies.Daos;

import java.util.List;

import com.entity.*;

public interface MoviesDao extends AutoCloseable{

	List<Movies> findAll() throws Exception;
	
	
	

}