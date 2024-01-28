package com.email.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/welcome")
    public String welcome(){

        String message = "This is message for security check";
        String subject = "CodersArea : Confirmation";
        String to = "TO EMAIL";
        String from = "FROM EMAIL";
        File file = new File("C:\\Users\\thaba\\Downloads\\dashboard.png");
        try {
            emailService.sendEmail(message, subject, to, from, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "This is Welcome page for Web API";

    }
    
}
