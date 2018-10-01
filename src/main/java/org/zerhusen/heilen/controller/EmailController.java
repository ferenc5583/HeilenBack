/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerhusen.heilen.model.EmailMessage;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Ferenc
 */
@RestController
public class EmailController {

    @Value("${gmail.username}")
    private String username;
    @Value("${gmail.password}")
    private String password;

    //mapping de la api para ejecutar la funcion sendemail
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public String sendEmail(@RequestBody EmailMessage emailmessage) throws AddressException, MessagingException, IOException {
        sendmail(emailmessage);
        JsonObject res = new JsonObject();
        res.addProperty("message", "Email enviado Correctamente");
        String json_res = res.toString();
        return json_res;
    }
    //funcion que envia el mail
    private void sendmail(EmailMessage emailmessage) throws AddressException, MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, false));
        
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailmessage.getTo_address()));
        
        msg.setSubject(emailmessage.getSubject());
        
        msg.setContent(emailmessage.getBody(), "text/html");
        
        msg.setSentDate(new Date());
        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailmessage.getBody(), "text/html");
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();
        
        attachPart.attachFile("C:\\Users\\Ferenc\\Pictures\\Imagen1.png");
        
        multipart.addBodyPart(attachPart);
        
        msg.setContent(multipart);
        //envia el mail
        Transport.send(msg);

    }
}
