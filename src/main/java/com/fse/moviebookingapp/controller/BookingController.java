package com.fse.moviebookingapp.controller;

import com.fse.moviebookingapp.dto.TicketDto;
import com.fse.moviebookingapp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@Validated
public class BookingController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/{movieName}/add")
    public ResponseEntity<?> bookTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable("movieName") String movieName) {
        ticketService.bookTicket(movieName, ticketDto);
        return ResponseEntity.ok("Ticket Booked Successfully for " + movieName + " movie");
    }

}
