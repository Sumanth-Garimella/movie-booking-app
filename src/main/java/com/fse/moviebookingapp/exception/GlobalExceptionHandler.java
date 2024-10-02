package com.fse.moviebookingapp.exception;

import com.fse.moviebookingapp.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(UserExceptions.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> userExceptions(UserExceptions ue){
        ErrorResponse errorResponse = new ErrorResponse("","");
        if(ue.getMessage().equalsIgnoreCase("PASSWORD_MISMATCH")) {
             errorResponse = new ErrorResponse(ue.getMessage(), "Please ensure that password and confirm password are same");
        }else if(ue.getMessage().equalsIgnoreCase("USER_EXISTS")){
             errorResponse = new ErrorResponse(ue.getMessage(), "Login ID already exists. Please try with different Login ID");
        }else if(ue.getMessage().equalsIgnoreCase("EMAIL_EXISTS")){
             errorResponse = new ErrorResponse(ue.getMessage(), "Email already exists. Please use different Email");
        }else if(ue.getMessage().equalsIgnoreCase("NOT_ENOUGH_TICKETS")){
            errorResponse = new ErrorResponse(ue.getMessage(),"Tickets Available are less than the Tickets Booked.");
        }else if(ue.getMessage().equalsIgnoreCase("MOVIE_NOT_FOUND")){
            errorResponse = new ErrorResponse(ue.getMessage(),"Movie not found with the Movie Name.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
