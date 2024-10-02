package com.fse.moviebookingapp.controller;

import com.fse.moviebookingapp.dto.MovieDto;
import com.fse.moviebookingapp.model.Movie;
import com.fse.moviebookingapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/movie/search/{movieName}")
    public List<Movie> searchMovie(@PathVariable("movieName") String movieName){
        return movieService.searchMovieName(movieName);
    }

    @PostMapping("/addMovie")
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieDto movieDto){
        Movie movie = movieService.saveMovie(movieDto);
        return  ResponseEntity.ok(movie.getMovieName()+" Movie Added Successfully");
    }

    @DeleteMapping("{movieName}/delete/{theatreName}")
    public ResponseEntity<?> deleteMovie(@PathVariable("movieName")String movieName,@PathVariable("theatreName")String theatreName){
        movieService.deleteMovie(movieName,theatreName);
        return ResponseEntity.ok(movieName +" Movie Deleted Successfully.");
    }
}
