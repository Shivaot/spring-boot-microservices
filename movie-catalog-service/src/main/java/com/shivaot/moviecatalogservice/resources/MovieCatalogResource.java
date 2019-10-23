package com.shivaot.moviecatalogservice.resources;

import com.shivaot.moviecatalogservice.models.CatalogItem;
import java.util.List;
import com.shivaot.moviecatalogservice.models.Movie;
import com.shivaot.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping(value="/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // Get all rated movie Ids
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId,
                UserRating.class);
        return ratings.getUserRating().stream().map(rating -> {
            // For each movie Id, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
                    Movie.class);
            // Put them all together
           return new CatalogItem(movie.getName(),"Desc",rating.getRating());

        }).collect(Collectors.toList());

    }
}





/* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
*/
