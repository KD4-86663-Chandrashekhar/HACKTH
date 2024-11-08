package com.movies.tester;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.entity.Movies;
import com.entity.Review;
import com.entity.Shares;
import com.entity.User;
import com.movies.Daos.MoviesDao;
import com.movies.Daos.MoviesDaoImpl;
import com.movies.Daos.ReviewDao;
import com.movies.Daos.ReviewDaoImpl;
import com.movies.Daos.ShareDao;
import com.movies.Daos.ShareDaoImpl;
import com.movies.Daos.UserDao;
import com.movies.Daos.UserDaoImpl;

public class tester1 {

	public static Scanner sc = null;
	public static User curUser = null;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		while (true) {
			System.out.print("0. Exit\n1. Sign In\n2. Sign Up\nEnter choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				System.exit(0);
				break;
			case 1:
				userAuthentication();
				break;
			case 2:
				userSignUp();
				break;
			}
		}
	}

	private static void userAuthentication() {
		try (UserDao userDao = new UserDaoImpl()) {
			System.out.print("Enter email: ");
			String email = sc.next();
			System.out.print("Enter passwd: ");
			String passwd = sc.next();
			User u = userDao.findByEmail(email);
			if (u != null && u.getPassword().equals(passwd)) {
				System.out.println("Login Successful: " + u);
				curUser = u;
				userMenu();

			} else
				System.out.println("Login Failed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void userSignUp() {
		try (UserDao userDao = new UserDaoImpl()) {
			System.out.print("Enter first name: ");
			String fName = sc.next();
			System.out.print("Enter last name: ");
			String lName = sc.next();
			System.out.print("Enter email: ");
			String email = sc.next();
			System.out.print("Enter mobile: ");
			String mobile = sc.next();
			System.out.print("Enter birth date (yyyy-MM-dd): ");
			String birth = sc.next();
			Date birthDate = Date.valueOf(birth);
			System.out.println("Enter the password");
			String pass = sc.next();
			User u = new User(0, fName, lName, email, mobile, birthDate, pass);
			int count = userDao.save(u);
			System.out.println("User Saved: " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void userMenu() {
		while (true) {
			System.out.print(
					"\n0. Log Out\n1.Edit Profile \n2.Change Password\n3.Write Review\n 4.Edit Review\n5.Display All MOvies \n6.Display All reviews\n7.Display My Reviews\n8.Display Review Shared with me \n9.Share Review\n10.Delete review: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				curUser = null;
				return;
			case 1:
				EditProfile();
				break;
			case 2:
				ChangePass();

				break;
			case 3:
				WriteReview();
				break;
			case 4:
				EditReview();
				break;
			case 5:
				showAllMovies();
				break;
			case 6:
				displayAllReview();
				break;
			case 7:
				displayMyReview();
				break;
			case 8:displaySharedReview();
				break;
			case 9:
				shareReview();
				break;
			case 10:
				deleteReview();
				break;
			}
		}
	}
//
//private static void displaySharedReview() 
//    {
//	 try(ReviewDao revDao = new ReviewDaoImpl())
//  	 {
//		 List<Review> list=revDao.SharedReviews();
//		 list.forEach(System.out::println);
//		 
//     }
//	 catch (Exception e) 
//	 {
//			
//			
//	}
//		
//	}
	private static void displaySharedReview() {
	    try (ReviewDao revDao = new ReviewDaoImpl()) {
	        List<Review> list = revDao.SharedReviews(curUser.getId()); 
	        list.forEach(System.out::println);
	        
	    } catch (Exception e) {
	        
	    }
	}


private static void shareReview() {
	displayMyReview();
	System.out.println("Enter the review id to be shared:");
	int id=sc.nextInt();
	try(ReviewDao revDao = new ReviewDaoImpl()) 
    {
    	 Review r=revDao.findById(id);
    	 System.out.println(r);
    	 
    			try(UserDao userDao = new UserDaoImpl())
    			{
    				
    				List<User> list=userDao.findAll();
    				 list.forEach(System.out::println);
    				 int count=0;
    				 int uid=-1;
    				 while(uid!=0)
    				 {
    					 uid=sc.nextInt();
    					 if(uid==curUser.getId())
    					 {
    					  System.out.println("You can Not share with yourself");
    					 }
    					 try(ShareDao shareDao = new ShareDaoImpl())
    		    	  	 {
    						 Shares s=new Shares(id,uid);
    						 int c= shareDao.save(s); 
    						 if(c==1)
    							    count++;
    				     }
    					 catch (Exception e) 
    					 {
    			    			
    			    			
    			    	}
    		       }
    				 System.out.println("review Shared with users: "+count);
    	
    } 
	catch (Exception e) {
	
	
          }
	
	
    } catch (Exception e1) {
		
		e1.printStackTrace();
}}

	private static void deleteReview() {
		displayMyReview();
		System.out.println("Enter review id to delete the review");
		int id = sc.nextInt();
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			int count = revDao.delete(id);
			try (ShareDao shDao = new ShareDaoImpl()) {
				 shDao.delete(id);
				 
			  }
			catch (Exception e) {

			         }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			displayMyReview();
		}



	private static void displayMyReview() {
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			List<Review> list = revDao.findAllMyReviews(curUser.getId());
			list.forEach(System.out::println);
		} catch (Exception e) {

		}

	}

	private static void displayAllReview() {
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			List<Review> list = revDao.findAllReviews();
			list.forEach(System.out::println);
		} catch (Exception e) {

		}

	}

	private static void EditReview() {
		displayMyReview();
		System.out.println("Enter revire id to be edited:");
		int id = sc.nextInt();
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			Review r = revDao.findById(id);
			System.out.println(r);
			System.out.println("Enter new rating");
			r.setRating(sc.nextInt());
			System.out.println("Enter new review");
			sc.nextLine();
			r.setReview(sc.nextLine());
			int count = revDao.update(r);
			System.out.println("Review Updated:" + count);
		} catch (Exception e) {

		}

	}

	private static void WriteReview() {
		showAllMovies();
		System.out.println("Enter the movie id to write review");
		int mv_id = sc.nextInt();
		System.out.println("Enter the rating");
		int rating = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the review");
		String rew = sc.nextLine();
		Timestamp instant = Timestamp.from(Instant.now());
		Review r = new Review(0, mv_id, rew, rating, curUser.getId(), instant);
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			int count = revDao.save(r);
			System.out.println("Review Saved " + count);
		} catch (Exception e) {

		}

	}

	private static void showAllMovies() {
		try (MoviesDao movieDao = new MoviesDaoImpl()) {
			List<Movies> list = movieDao.findAll();
			list.forEach(System.out::println);
		} catch (Exception e) {

		}

	}

	private static void ChangePass() {
		try (UserDao userDao = new UserDaoImpl()) {

			System.out.println("Enter the New password");
			String pass = sc.next();
			User u = new User(curUser.getId(), curUser.getFirstName(), curUser.getLastName(), curUser.getEmail(),
					curUser.getEmail(), curUser.getBirth(), pass);
			int count = userDao.updatePassword(u);
			System.out.println("User Updated: " + count);
			System.out.println("Password Updated");
		} catch (Exception e) {

		}

	}

	private static void EditProfile() {
		try (UserDao userDao = new UserDaoImpl()) {
			System.out.print("Enter first name: ");
			String fName = sc.next();
			System.out.print("Enter last name: ");
			String lName = sc.next();
			System.out.print("Enter email: ");
			String email = sc.next();
			System.out.print("Enter mobile: ");
			String mobile = sc.next();
			System.out.print("Enter birth date (yyyy-MM-dd): ");
			String birth = sc.next();
			Date birthDate = Date.valueOf(birth);
			System.out.println("Enter the password");
			String pass = sc.next();
			User u = new User(curUser.getId(), fName, lName, email, mobile, birthDate, pass);
			int count = userDao.update(u);
			System.out.println("User Updated: " + count);
		} catch (Exception e) {

		}
	}
}
