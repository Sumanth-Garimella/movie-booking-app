package com.fse.moviebookingapp.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fse.moviebookingapp.dto.TicketDto;
import com.fse.moviebookingapp.model.Ticket;
import com.fse.moviebookingapp.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Service
@Slf4j
public class TicketConsumerService {

    @Autowired
    private TicketRepository ticketRepository;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void consume(String message) {
        log.info("Consumed Message from the topic: {}",message);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()), new JacksonAnnotationIntrospector()));
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            TicketDto ticketdto = objectMapper.readValue(message, TicketDto.class);
            Ticket ticket = new Ticket();
            ticket.setMovieName(ticketdto.getMovieName());
            ticket.setTheatreName(ticketdto.getTheatreName());
            ticket.setNumberOfTickets(ticketdto.getNumberOfTickets());
            ticket.setSeatNumber(ticketdto.getSeatNumber());
            ticketRepository.save(ticket);
            log.info("Ticket Saved in the Repository: {}",ticket);
        } catch (Exception e){
            log.error("Error Processing Message: {}",e.getMessage());
        }
    }
}
