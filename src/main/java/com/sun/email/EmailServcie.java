package com.sun.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServcie {
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String userName) {

        String link = "http://localhost:8080/";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to SharanIt App.....!!");
        message.setText("Hi " + userName + ",\n\n"
                + "Thank you for registering with us!!\n"
                + "Please reset your password by clicking the link below:\n"
                + link + "\n\n"
                + "Regards,\nYour Company");

        mailSender.send(message);
    }

}
