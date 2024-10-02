package com.fse.moviebookingapp.repository;

import com.fse.moviebookingapp.model.Movie;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query("{'movieName':{ $regex:?0,$options:'i'}}")
    List<Movie> findByMovieNameRegex(String regex);

    default Movie findMovieByMovieAndTheatreName(String movieName, String theatreName, MongoTemplate mongoTemplate){
        AggregationOperation match = Aggregation.match(Criteria.where("movieName").is(movieName).and("theatreName").is(theatreName));
        Aggregation aggregation = Aggregation.newAggregation(match);
        return mongoTemplate.aggregate(aggregation,"Movie",Movie.class).getUniqueMappedResult();
    }

    void deleteByMovieNameAndTheatreName(String movieName, String theatreName);
}
