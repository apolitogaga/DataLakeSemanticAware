package dataAmazonType;

import java.util.ArrayList;

public class Movie {
	private String name;
	private ArrayList<Double> ratings;
	private int idMovie;
	private double average;

	// URI myNewURI; //this is maybe for later creation.
	public Movie(String name, int id) {
		this.name = name;
		this.idMovie = id;
		this.average = -1;
		this.ratings = new ArrayList<Double>();
	}

	public Movie(String name, int id, double rating) {
		this(name, id); // call the constructor with a string as a parameter,
						// (the one above this one).
		this.ratings.add(rating);
		updateAverage();
	}

	public void addRatingValue(double rating) {
		ratings.add(rating);
		updateAverage();
	}

	private void updateAverage() {
		this.average = getAverage();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		// get an average rating
		return 2;
	}

	public void newRating(int rating) {
		// this.rating = rating;
	}

	@Override
	public String toString() {
		String ratingStrings = "";
		for (double rating : ratings)
			ratingStrings += "<>" + rating;
		return Integer.toString(idMovie) + "::" + name + "::"
				+ Double.toString(average) + ">>>" + ratingStrings;
	}
	
	private double getAverage() {
		return ratings.stream().mapToDouble(Double::doubleValue).average()
				.getAsDouble();
	}
}
