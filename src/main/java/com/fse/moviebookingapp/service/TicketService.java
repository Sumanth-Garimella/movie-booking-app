package com.fse.moviebookingapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebookingapp.dto.TicketDto;
import com.fse.moviebookingapp.exception.UserExceptions;
import com.fse.moviebookingapp.model.Movie;
import com.fse.moviebookingapp.model.Ticket;
import com.fse.moviebookingapp.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String TOPIC = "booked_tickets";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void bookTicket(String movieName, TicketDto ticketDto){
        Movie movie = movieService.getMovieByMovieAndTheatreName(ticketDto.getMovieName(),ticketDto.getTheatreName());
        if(movie!=null&&movie.getTotalTickets()>=ticketDto.getNumberOfTickets()){
            movie.setTotalTickets(movie.getTotalTickets()-ticketDto.getNumberOfTickets());
            if(movie.getTotalTickets()==0){
                movieService.updateMovieStatus(movie,"SOLD_OUT");
                log.info("Tickets Not Available For {} Movie. Status Updated as SOLD_OUT",movie.getMovieName());
            }else{
                movieService.updateMovieStatus(movie,"BOOK_ASAP");
                log.info("Tickets Available For {} Movie. Status Updated as BOOK_ASAP",movie.getMovieName());
            }
            movieService.updateTotalTickets(movie);
            try {
                String message = objectMapper.writeValueAsString(ticketDto);
                log.info("Message Published into the Topic: {}",message);
                kafkaTemplate.send(TOPIC, message);
            } catch (JsonProcessingException e) {
                log.error("Unable to publish the message into the topic");
                e.printStackTrace();
            }
        }else if(movie==null){
            log.error("Movie not found with Movie Name: {}",ticketDto.getMovieName());
            throw new UserExceptions("MOVIE_NOT_FOUND");
        }else {
            log.error("Not Enough tickets availble for the {} Movie",ticketDto.getMovieName());
            throw new UserExceptions("NOT_ENOUGH_TICKETS");
        }
    }
    public List<Ticket> getTicketsByMovieNameAndTheatreName(String movieName, String theatreName){
        return ticketRepository.findByMovieNameAndTheatreName(movieName,theatreName,mongoTemplate);
    }
    public List<Ticket> getTicketsByMovieName(String movieName){
        return ticketRepository.findByMovieName(movieName);
    }
}
