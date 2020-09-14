package com.vish.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vish.moviecatalogservice.models.Rating;

@Service
public class RatingService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackRating")
	public Rating getRating(String movieId) {
		Rating rating = restTemplate.getForObject("http://movie-rating-service/ratings/" + movieId, Rating.class);
		return rating;
	}

	public Rating getFallbackRating(@PathVariable("movieId") String movieId) {
		Rating rating = new Rating();
		rating.setMovieId(movieId);
		rating.setRating("Rating service down");
		return rating;
	}

}
