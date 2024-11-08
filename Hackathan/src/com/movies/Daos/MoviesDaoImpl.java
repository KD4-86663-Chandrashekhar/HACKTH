package com.movies.Daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Movies;
import com.entity.User;

public class MoviesDaoImpl extends Dao implements MoviesDao {
	 
		
		private PreparedStatement stmtFindAll;
		
		public MoviesDaoImpl() throws Exception {
			 String sqlFindAll="SELECT * FROM movies;";
			 stmtFindAll =con.prepareStatement(sqlFindAll);
		}
		@Override
		public List<Movies> findAll() throws Exception {
			List<Movies> list= new ArrayList<Movies>();
			try(ResultSet rst=stmtFindAll.executeQuery())
			{
				while(rst.next())
				{
					int id = rst.getInt("id");
					String title = rst.getString("title");
					Date rel_date = rst.getDate("rel_date");
					Movies m = new Movies(id,title ,rel_date);
					list.add(m);
					
					
				}
			}
			
			return list;
	}
}
		
		
		

