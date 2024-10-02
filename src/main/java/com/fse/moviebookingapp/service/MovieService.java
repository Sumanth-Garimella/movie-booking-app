package com.fse.moviebookingapp.service;

import com.fse.moviebookingapp.dto.MovieDto;
import com.fse.moviebookingapp.exception.UserExceptions;
import com.fse.moviebookingapp.model.Movie;
import com.fse.moviebookingapp.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> searchMovieName(String movieName){
        return movieRepository.findByMovieNameRegex(movieName);
    }

    public Movie getMovieByNameAndTheatreName(String movieName, String theatreName){
        return movieRepository.findById(movieName).orElse(null);
    }

    public Movie saveMovie(MovieDto movieDto){
        Movie movie = new Movie();
        movie.setMovieName(movieDto.getMovieName());
        movie.setTheatreName(movieDto.getTheatreName());
        movie.setTotalTickets(movieDto.getTotalTickets());
        movie.setStatus("BOOK ASAP");
        movieRepository.save(movie);
        log.info("{} Movie Added to the List Of Movies",movieDto.getMovieName());
        return movie;
    }

    public void updateMovieStatus(Movie movie, String status){
        movie.setStatus(status);
        movieRepository.save(movie);
        log.info("Movie Status was Updated Successfully as {}",status);
    }

    public Movie getMovieByMovieAndTheatreName(String movieName, String theatreName){
        return movieRepository.findMovieByMovieAndTheatreName(movieName,theatreName,mongoTemplate);
    }

    public void deleteMovie(String movieName,String theatreName){
        if (getMovieByMovieAndTheatreName(movieName,theatreName)!=null) {
            movieRepository.deleteByMovieNameAndTheatreName(movieName, theatreName);
            log.info("{} from the {} theatre got Deleted Successfully", movieName,theatreName);
        }else{
            log.error("There is no movie the Movie Name: {} in theatre: {}",movieName,theatreName);
            throw new UserExceptions("MOVIE_NOT_FOUND");
        }
    }

    public void updateTotalTickets(Movie movie){
        movieRepository.save(movie);
    }
}
