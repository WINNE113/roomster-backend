package com.roomster.roomsterbackend.mailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
    	System.out.println("HERE WE GO");
    	System.out.println("gmail " + to);
    	System.out.println("subject " + subject);
    	System.out.println("text " + text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}