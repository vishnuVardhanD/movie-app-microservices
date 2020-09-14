package com.vish.moviecatalogservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vish.moviecatalogservice.models.CatalogItem;
import com.vish.moviecatalogservice.models.Movie;
import com.vish.moviecatalogservice.models.Rating;
import com.vish.moviecatalogservice.services.MovieInfoService;
import com.vish.moviecatalogservice.services.RatingService;

/**
 * @author Vishnu Vardhan
 *
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	MovieInfoService movieInfoService;

	@Autowired
	RatingService ratingService;

	@RequestMapping("/{movieId}")
	public CatalogItem getCatalogItem(@PathVariable("movieId") String movieId) {

		Rating rating = ratingService.getRating(movieId);
		Movie movieInfo = movieInfoService.getMovieInfo(rating);

		return new CatalogItem(movieInfo.getId(), movieInfo.getName(), rating.getRating());
	}

}
