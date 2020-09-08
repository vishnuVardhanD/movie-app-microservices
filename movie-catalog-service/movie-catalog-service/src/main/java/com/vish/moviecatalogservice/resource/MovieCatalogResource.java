package com.vish.moviecatalogservice.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vish.moviecatalogservice.models.CatalogItem;
import com.vish.moviecatalogservice.models.Movie;
import com.vish.moviecatalogservice.models.Rating;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
    private RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/{movieId}")
	public CatalogItem getCatalogItem(@PathVariable("movieId") String movieId) {
		
		Movie movieInfo = restTemplate.getForObject("http://localhost:8082/movies/" + movieId, Movie.class);
        Rating rating = restTemplate.getForObject("http://localhost:8083/ratings/" + movieId, Rating.class);
       
		return new CatalogItem(movieInfo.getId(), movieInfo.getName(), rating.getRating());
    }

}
