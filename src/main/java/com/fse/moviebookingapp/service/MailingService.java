package com.fse.moviebookingapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailingService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String email, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registration Confirmation");
        message.setText("Thank you for registering. Your Username is :" + username);
        mailSender.send(message);
        log.info("Email sent to the User with LoginID");
    }
}
