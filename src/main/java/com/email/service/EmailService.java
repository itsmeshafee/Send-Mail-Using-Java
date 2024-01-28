package com.email.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailService {
    

    
    public void sendEmail(String message,String subject, String to,String from, File file) throws IOException{

        //Validate for Gmail
        String host = "smtp.gmail.com";

        //get system properties
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        // properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {                
                return new PasswordAuthentication(from, "YOUR PASSWORD");
            }
            
        });

        session.setDebug(true);

        //compose message
        MimeMessage m = new MimeMessage(session);
        
        try {
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            //for Text
            MimeBodyPart part1 = new MimeBodyPart();
            part1.setText(message);

            //for Attachment
            MimeBodyPart part2 = new MimeBodyPart();
            part2.attachFile(file);

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(part1);
            mimeMultipart.addBodyPart(part2);

            // m.setText(message);
            // Transport.send(mimeMultipart);
            m.setContent(mimeMultipart);
            Transport.send(m);
            System.out.println("Send");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }


}
