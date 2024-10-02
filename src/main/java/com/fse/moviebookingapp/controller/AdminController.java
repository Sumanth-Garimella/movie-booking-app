package com.fse.moviebookingapp.controller;

import com.fse.moviebookingapp.dto.RegistrationDto;
import com.fse.moviebookingapp.model.User;
import com.fse.moviebookingapp.service.AdminService;
import com.fse.moviebookingapp.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    MailingService mailingService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> userRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        adminService.registerUser(registrationDto);
        mailingService.sendMail(registrationDto.getEmail(), registrationDto.getLoginId());
        return ResponseEntity.ok("User " + registrationDto.getLoginId() + " Registered Successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam(required = false) String loginId, @RequestParam(required = false) String password, HttpSession session) {
        if (loginId == null || loginId.isEmpty()) {
            return ResponseEntity.badRequest().body("Login ID is Required");
        } else if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is Required");
        }
        Optional<User> user = adminService.authenticate(loginId, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
    }

    @GetMapping("{loginId}/forgot")
    public ResponseEntity<?> forgotPassword(@PathVariable String loginId, HttpSession session) {
        Optional<User> user = adminService.findUserByLoginId(loginId);
        if (user.isPresent()) {
            session.setAttribute("user",user.get());
            return ResponseEntity.ok("Login ID exists. You can change the password");
        } else {
            return ResponseEntity.status(404).body("Login ID does not Exists.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetUserPassword(@RequestParam String newPassword, @RequestParam String confirmNewPassword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ResponseEntity.badRequest().body("No User Found to reset password");
        }
        if (adminService.resetUserPassword(user, newPassword, confirmNewPassword)) {
            return ResponseEntity.ok("Password Reset Successful");
        }else{
            return ResponseEntity.badRequest().body("Failed to reset passoword. Please try again");
        }
    }
}
