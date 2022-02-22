package com.alkemy.ong.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


public class EmailService {

    @Value("${sendgrid.api.key}")
    private final String apiKey;
    @Value("${alkemy.ong.email.sender}")
    private final String emailSender;

    public EmailService(String apiKey, String emailSender) {
        this.apiKey = apiKey;
        this.emailSender = emailSender;
    }

    @Transactional
    public void sendEmailTo(String to){
        Email fromEmail= new Email(emailSender);
        Email toEmail= new Email(to);
        Content content= new Content("text/plain", "Proyecto ONG - Somos Más");
        String subject= "Proyecto ONG - Somos Más";

        Mail mail=new Mail(fromEmail,subject,toEmail,content);
        SendGrid sendGrid=new SendGrid(apiKey);
        Request request= new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response=sendGrid.api(request);

        }catch (IOException ex){
            System.out.println("Error trying to send the email");
        }

    }
}
