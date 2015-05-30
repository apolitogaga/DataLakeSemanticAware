package dataAmazonType;

public class Rating {
	double rating;
	User user;
	Movie movie;
	public Rating(double rating, User user, Movie movie) {
		super();
		this.rating = rating;
		this.user = user;
		this.movie = movie;
	}
	public String getMovieRating(){
		return movie.getName() +">"+ Double.toString(rating);
	}
	public String getUserRating(){
		return user.getUsername() +">"+ Double.toString(rating);
	}
}
