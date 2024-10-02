package com.fse.moviebookingapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class MovieDto {

    @NotEmpty(message = "Movie Name is Required")
    private String movieName;
    @NotEmpty(message = "Theatre Name is Required")
    private String theatreName;
    @Positive(message = "Total Tickets is Required and Should be Greater than 0.")
    private int totalTickets;

}
