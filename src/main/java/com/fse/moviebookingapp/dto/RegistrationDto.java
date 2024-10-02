package com.fse.moviebookingapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationDto {

    @NotEmpty(message = "First Name is Required")
    private String firstName;
    @NotEmpty(message = "Last Name is Required")
    private String lastName;
    @NotEmpty(message = "Password is Required")
    private String password;
    @NotEmpty(message = "Confirm Password must be provided and should be same as Password")
    private String confirmPassword;
    @NotEmpty(message = "Email is Required")
    private String email;
    @NotEmpty(message = "Login ID is Required")
    private String loginId;
    @NotEmpty(message = "Contact Number is required")
    private String contactNumber;

}
