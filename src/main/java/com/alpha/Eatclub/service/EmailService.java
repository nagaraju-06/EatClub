package com.alpha.Eatclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationMail(String toEmail, String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Welcome to EatClub 🎉");
        message.setText(
                "Hello " + name + ",\n\n" +
                "Your registration was successful.\n" +
                "Welcome to EatClub 🍔\n\n" +
                "Thank you!"
        );

        mailSender.send(message);
        System.out.println("EMAIL METHOD CALLED");
    }
}
