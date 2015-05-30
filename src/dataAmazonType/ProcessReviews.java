package dataAmazonType;

import java.util.HashMap;

public class ProcessReviews {
	private static HashMap<String, Movie> movies;
	private static HashMap<String, User> reviewers;
	private static int idMovies;
	private static int idReviewers;
	
	public ProcessReviews(){
		movies = new HashMap<String, Movie>();
		reviewers = new HashMap<String, User>();
		idMovies=0;
		idReviewers=0;
	}
	public void newReview(String title, String sRating, String reviewer)
	{	
		Movie tempMovie;
		User tempUser;
		Rating tempRating;
		double rating = (Double) Double.parseDouble(sRating);
		if(!reviewers.containsKey(reviewer)){
			//Create a new object type user
			tempUser = new User(reviewer, idReviewers++);
			reviewers.put(reviewer,tempUser);	
		}
		else{
			tempUser = reviewers.get(reviewer);
		}
		if(!movies.containsKey(title)){
			tempMovie = new Movie(title, idMovies++, rating);
			movies.put(title,tempMovie);
		}
		else{
			tempMovie = movies.get(title);
			tempMovie.addRatingValue(rating);
		}
		
		tempRating = new Rating(rating, tempUser, tempMovie);
		tempUser.addRating(tempRating);	
		// using the unlabeled dataset we have 122438  reviews.
		// for the other two we have around 1000 reviews.
	}
	public void test(){
		System.out.println(movies.size() +"  <>  " + idMovies + " <> " + reviewers.size() + "  <>  " + idReviewers);
		System.out.println(movies.containsKey("NBA Furious Finishes"));
		
	}
	public void testAll()
	{
		for(String key: movies.keySet()){
			//System.out.println(key);
		    System.out.println(movies.get(key));
		}
//		for(String key: this.reviewers.keySet()){
//			//System.out.println(key);
//		    System.out.println(this.reviewers.get(key));
//		}
	}
}
