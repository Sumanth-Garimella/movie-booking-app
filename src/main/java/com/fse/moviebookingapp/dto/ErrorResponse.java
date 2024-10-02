package com.fse.moviebookingapp.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private String error;
    private String info;

    public ErrorResponse(String error, String info) {
        this.error = error;
        this.info = info;
    }
}
