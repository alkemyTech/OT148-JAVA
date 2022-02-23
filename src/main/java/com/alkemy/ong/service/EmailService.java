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

    private final String emailSender;
    private final SendGrid sendGrid;

    public EmailService(String apiKey, String emailSender) {
        this.emailSender = emailSender;
        this.sendGrid = new SendGrid(apiKey);
    }

    public void sendEmailTo(String to,String message, String subject){
        Email fromEmail= new Email(emailSender);
        Email toEmail= new Email(to);
        Content content = new Content("text/html",message);
        Mail mail=new Mail(fromEmail,subject,toEmail,content);
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

    public void welcomeEmail(String email,String username){
        String message= "Bienvenid@ " + username +" !!";
        String subject = "Welcome ONG-Somos Más";

        sendEmailTo(email,message,subject);
    }

}
