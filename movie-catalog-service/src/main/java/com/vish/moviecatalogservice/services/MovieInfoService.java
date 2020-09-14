package com.vish.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vish.moviecatalogservice.models.Movie;
import com.vish.moviecatalogservice.models.Rating;

@Service
public class MovieInfoService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
	public Movie getMovieInfo(Rating rating) {
		Movie movieInfo = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
				Movie.class);
		return movieInfo;
	}

	public Movie getFallbackMovieInfo(Rating rating) {
		Movie movie = new Movie();
		movie.setId("0");
		movie.setName("Movie not found");
		movie.setDesc("Fault");
		return movie;
	}

}
