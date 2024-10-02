package com.fse.moviebookingapp.repository;

import com.fse.moviebookingapp.model.Ticket;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByMovieName(String movieName);
    default List<Ticket> findByMovieNameAndTheatreName(String movieName, String theatreName, MongoTemplate mongoTemplate){
        AggregationOperation match = Aggregation.match(Criteria.where("movieName").is(movieName).and("theatreName").is(theatreName));
        Aggregation aggregation = Aggregation.newAggregation(match);
        return mongoTemplate.aggregate(aggregation,"Tickets",Ticket.class).getMappedResults();
    }
}
