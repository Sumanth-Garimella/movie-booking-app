package com.fse.moviebookingapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Movie")
public class Movie {

    @Id
    private String id;
    private String movieName;
    private String theatreName;
    private int totalTickets;
    private String status;
    @DBRef
    private List<Ticket> tickets;
}
