package com.alkemy.ong.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class EmailService {

    private final String to;
    @Value("${sendgrid.api.key}")
    private String apiKey;
    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    
    public EmailService(String to){
        this.to = to;
    }

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
