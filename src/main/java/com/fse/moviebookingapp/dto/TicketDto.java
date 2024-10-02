package com.fse.moviebookingapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fse.moviebookingapp.util.TicketDtoDeserializer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Data
@JsonDeserialize(using = TicketDtoDeserializer.class)
public class TicketDto implements Serializable {

    @NotEmpty(message = "Movie Name is Required")
    private String movieName;
    @NotEmpty(message = "Theatre Name is Required")
    private String theatreName;
    @Positive(message = "Number of Tickets is Required and should be greater than zero.")
    private int numberOfTickets;
    @NotEmpty(message = "Seat Number selected are required and Cannot be empty. Please ensure count of seats selected should be equal to Number of seats.")
    private List<String> seatNumber;

    public TicketDto(String movieName, String theatreName, int numberOfTickets, List<String> seatNumber) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.numberOfTickets = numberOfTickets;
        this.seatNumber = seatNumber;
    }
}
