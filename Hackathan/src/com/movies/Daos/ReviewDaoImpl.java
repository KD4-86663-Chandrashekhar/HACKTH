package com.movies.Daos;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.entity.Review;
import com.entity.User;

public class ReviewDaoImpl extends Dao implements ReviewDao {

	private PreparedStatement stmtFindById;
	private PreparedStatement stmtSave;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtFindAllReviews;
	private PreparedStatement stmtFindAllMyReviews;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtSharedReview;
	
	
	
	public ReviewDaoImpl() throws Exception {
		 String sqlSave="Insert into reviews(movie_id,review,rating,user_id,modified) values(?,?,?,?,?);";
		 stmtSave =con.prepareStatement(sqlSave);
		 String sqlFindById="SELECT * FROM reviews where id=?;";
		 stmtFindById =con.prepareStatement(sqlFindById);
		 String sqlUpdate = "UPDATE reviews SET review=?,rating=?,modified=? WHERE id=?";
		 stmtUpdate = con.prepareStatement(sqlUpdate);
		 String sqlFindAllReviews = "SELECT * from reviews";
		 stmtFindAllReviews = con.prepareStatement(sqlFindAllReviews);
		 String sqlFindAllMyReviews = "SELECT * from reviews where user_id=?";
		 stmtFindAllMyReviews = con.prepareStatement(sqlFindAllMyReviews);
		 String sqldelete = "delete from reviews where id=?";
		 stmtDelete = con.prepareStatement(sqldelete);
		 String sqlshared="SELECT reviews.id, reviews.movie_id, reviews.review, reviews.rating, reviews.user_id, reviews.modified FROM reviews INNER JOIN shares ON reviews.id = shares.review_id INNER JOIN users ON shares.user_id = ?";
		 stmtSharedReview=con.prepareStatement(sqlshared);
		 
		 
	}
	
	@Override
	public int save(Review r) throws Exception {
		stmtSave.setInt(1, r.getMovie_id());
		stmtSave.setString(2, r.getReview());
		stmtSave.setInt(3, r.getRating());
		stmtSave.setInt(4, r.getUser_id());
		stmtSave.setTimestamp(5,r.getModified());
		int count = stmtSave.executeUpdate();
		return count;
	}
	@Override
	public int update(Review r) throws Exception 
	{ 
		stmtUpdate.setString(1,r.getReview());
	    stmtUpdate.setInt(2, r.getRating());
	    stmtUpdate.setTimestamp(3, r.getModified());
	     stmtUpdate.setInt(4,r.getId());
	     int count = stmtUpdate.executeUpdate();
	    return count;
	
	
	}
	@Override
	public List<Review> findAllReviews() throws Exception {
		List<Review> list= new ArrayList<Review>();
		try(ResultSet rst=stmtFindAllReviews.executeQuery())
		{
			while(rst.next())
			{
				int id = rst.getInt("id");
				int mv_id=rst.getInt("movie_id");
				String rev=rst.getString("review");
				int rating=rst.getInt("rating");
				int user_id=rst.getInt("user_id");
				Timestamp timestamp=rst.getTimestamp("modified");
				Review r= new Review(id, mv_id, rev, rating, user_id, timestamp);
				list.add(r);
				
				
			}
		}
		
		return list;
	}
	@Override
	public List<Review> findAllMyReviews(int uid) throws Exception 
	{
		stmtFindAllMyReviews.setInt(1,uid );
		List<Review> list= new ArrayList<Review>();
		try(ResultSet rst=stmtFindAllMyReviews.executeQuery())
		{
			while(rst.next())
			{
				int id = rst.getInt("id");
				int mv_id=rst.getInt("movie_id");
				String rev=rst.getString("review");
				int rating=rst.getInt("rating");
				int user_id=rst.getInt("user_id");
				Timestamp timestamp=rst.getTimestamp("modified");
				Review r= new Review(id, mv_id, rev, rating, user_id, timestamp);
				list.add(r);
				
				
			}
		}
		
		return list;
	}

	@Override
	public Review findById(int id) throws Exception {
		stmtFindById.setInt(1, id);
		try(ResultSet rst=stmtFindById.executeQuery())
		{
			if(rst.next())
			{
			    id = rst.getInt("id");
				int mv_id=rst.getInt("movie_id");
				String rev=rst.getString("review");
				int rating=rst.getInt("rating");
				int user_id=rst.getInt("user_id");
				Timestamp timestamp=rst.getTimestamp("modified");
				Review r= new Review(id, mv_id, rev, rating, user_id, timestamp);
				return r;
				
				
			}
		}
		
		
		return null;
	}

	@Override
	public int delete(int id) throws Exception {
	    stmtDelete.setInt(1, id);
	    int count=stmtDelete.executeUpdate();
	    return count;
	}

//	@Override
//	public List<Review> SharedReviews() throws Exception {
//		List<Review> list= new ArrayList<Review>();
//		try(ResultSet rst=stmtSharedReview.executeQuery())
//		{
//			while(rst.next())
//			{
//				int id = rst.getInt("id");
//				int mv_id=rst.getInt("movie_id");
//				String rev=rst.getString("review");
//				int rating=rst.getInt("rating");
//				int user_id=rst.getInt("user_id");
//				Timestamp timestamp=rst.getTimestamp("modified");
//				Review r= new Review(id, mv_id, rev, rating, user_id, timestamp);
//				list.add(r);
//				
//				
//			}
//		}
//		
//		return list;
//		
//	}
	public List<Review> SharedReviews(int userId) throws Exception {
	    List<Review> list = new ArrayList<>();
	    stmtSharedReview.setInt(1, userId); 
	    try (ResultSet rst = stmtSharedReview.executeQuery()) {
	        while (rst.next()) {
	            int id = rst.getInt("id");
	            int mv_id = rst.getInt("movie_id");
	            String rev = rst.getString("review");
	            int rating = rst.getInt("rating");
	            int user_id = rst.getInt("user_id");
	            Timestamp timestamp = rst.getTimestamp("modified");
	            Review r = new Review(id, mv_id, rev, rating, user_id, timestamp);
	            list.add(r);
	        }
	    }
	    return list;
	}

	




}
