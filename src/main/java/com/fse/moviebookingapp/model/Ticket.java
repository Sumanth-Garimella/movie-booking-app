package com.fse.moviebookingapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Tickets")
public class Ticket {

    @Id
    private String id;
    private String movieName;
    private String theatreName;
    private int numberOfTickets;
    private List<String> seatNumber;


    public Ticket(String movieName, String theatreName, int numberOfTickets, List<String> seatNumber) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.numberOfTickets = numberOfTickets;
        this.seatNumber = seatNumber;
    }

}
