package com.shivaot.movieinfoservice.resources;

import com.shivaot.movieinfoservice.models.Movie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @RequestMapping(value = "/{movieId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovieInfo(@PathVariable("movieId") String movieId){
        return new Movie(movieId,"Test name");
    }
}
