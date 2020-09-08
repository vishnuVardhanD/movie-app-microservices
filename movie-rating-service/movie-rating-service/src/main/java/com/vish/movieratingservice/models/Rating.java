package com.vish.movieratingservice.models;

public class Rating {
	
	private String rating;
	private String movieId;
	
	public Rating(String movieId,String rating) {
		this.rating=rating;
		this.movieId =movieId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	

}
