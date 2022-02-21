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

@Service
public class EmailService {

    @Autowired
    private Environment env;

    private String emailSender;

    private boolean enable;

    public void sendEmailTo(String to){
        if(!enable){
            return;
        }
        String apiKey= env.getProperty("EMAIL_API_KEY");

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

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        }catch (IOException ex){
            System.out.println("Error trying to send the email");
        }

    }
}
