package com.vish.moviecatalogservice.models;

public class CatalogItem {
	private String movieId;
	private String movieName;
	private String rating;

	public CatalogItem(String movieId, String movieName, String rating) {
		this.movieId = movieId;
		this.movieName = movieName;
		this.rating = rating;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
