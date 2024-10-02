package com.fse.moviebookingapp.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String loginId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String password;
    private String confirmPassword;
}
