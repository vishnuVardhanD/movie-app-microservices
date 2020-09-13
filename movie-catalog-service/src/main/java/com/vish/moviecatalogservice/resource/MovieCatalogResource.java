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
	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public CatalogItem getCatalogItem(@PathVariable("movieId") String movieId) {
		
		Movie movieInfo = restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);
        Rating rating = restTemplate.getForObject("http://movie-rating-service/ratings/" + movieId, Rating.class);
       
		return new CatalogItem(movieInfo.getId(), movieInfo.getName(), rating.getRating());
    }
	
	public CatalogItem getFallbackCatalog(@PathVariable("movieId") String movieId) {
		return new CatalogItem(movieId, "No Movie", "No Rating");
	}

}
