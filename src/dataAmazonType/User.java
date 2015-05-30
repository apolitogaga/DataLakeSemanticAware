package dataAmazonType;

import java.util.ArrayList;

public class User {
	private String username;

	private int id;
	private ArrayList<Rating> ratings;
	public User(String username, int id) {
		super();
		this.username = username;
		this.id = id;
		ratings = new ArrayList<Rating>();
	}
	public User(String username, int id, Rating rating){
		this(username,id);
		ratings.add(rating);
	}
	public void addRating(Rating rating ){
		ratings.add(rating);
	}
	public String toString(){
		String ratingStrings="";
		for(Rating rating : ratings)
			ratingStrings+= "<>"+rating.getMovieRating();
		return Integer.toString(id)+ "::"+ username+"::"+ratingStrings;
	}
	public String getUsername() {
		return username;
	}
}
