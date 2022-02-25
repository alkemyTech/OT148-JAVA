package com.alkemy.ong.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;


public class EmailService {

    private final String emailSender;
    private final SendGrid sendGrid;
    private final String welcome;

    public EmailService(String apikey, String emailSender, String welcome) {
        this.emailSender = emailSender;
        this.sendGrid = new SendGrid(apikey);
        this.welcome = welcome;
    }

    public Response sendEmailTo(String to, String messege, String subject) {
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content1 = new Content("text/html", messege);
        Mail mail = new Mail(fromEmail, subject, toEmail, content1);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);

        } catch (IOException ex) {
            System.out.println("Error trying to send the email");
        }
        return response;
    }

    public void welcomeEmail(String email) {
        String subject = "Welcome ONG-Somos Más";
        String messege = this.welcome;

        this.sendEmailTo(email, messege, subject);
    }
}
