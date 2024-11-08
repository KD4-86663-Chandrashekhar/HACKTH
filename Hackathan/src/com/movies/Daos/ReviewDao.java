package com.movies.Daos;

import java.util.List;

import com.entity.Review;
import com.entity.User;


public interface ReviewDao extends AutoCloseable{

	
	Review findById(int id) throws Exception;
	int save(Review r) throws Exception;
	int update(Review u) throws Exception;
	List<Review> findAllReviews() throws Exception;
	List<Review> findAllMyReviews(int id) throws Exception;
    int delete(int id) throws Exception;
   // List<Review> SharedReviews() throws Exception;
    List<Review> SharedReviews(int userId) throws Exception;
	
	

}


