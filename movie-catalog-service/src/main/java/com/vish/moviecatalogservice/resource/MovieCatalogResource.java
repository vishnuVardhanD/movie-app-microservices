package com.vish.moviecatalogservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vish.moviecatalogservice.models.CatalogItem;
import com.vish.moviecatalogservice.models.Movie;
import com.vish.moviecatalogservice.models.Rating;


/**
 * @author Vishnu Vardhan
 *
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public CatalogItem getCatalogItem(@PathVariable("movieId") String movieId) {
		
		Movie movieInfo = getMovieInfo(movieId);
        Rating rating = getRating(movieId);
       
		return new CatalogItem(movieInfo.getId(), movieInfo.getName(), rating.getRating());
    }

	@HystrixCommand(fallbackMethod = "getFallbackRating")
	private Rating getRating(String movieId) {
		Rating rating = restTemplate.getForObject("http://movie-rating-service/ratings/" + movieId, Rating.class);
		return rating;
	}

	@HystrixCommand(fallbackMethod = "getFallbackMovie")
	private Movie getMovieInfo(String movieId) {
		Movie movieInfo = restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);
		return movieInfo;
	}
	
	public CatalogItem getFallbackMovie(@PathVariable("movieId") String movieId) {
		Movie movieInfo = new Movie();
		movieInfo.setId(movieId);
		movieInfo.setName("No Movie found");
		movieInfo.setDesc("Movie Info service down");
		return new CatalogItem(movieId, movieInfo.getName(), movieInfo.getDesc());
	}
	
	public CatalogItem getFallbackRating(@PathVariable("movieId") String movieId) {
		 Rating rating = new Rating();
		 rating.setMovieId(movieId);
		 rating.setRating("Rating service down");
		return new CatalogItem(movieId, "No Movie found", rating.getRating());
	}
	
	

}
