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
    private final String emailTemplate;

    public EmailService(String apikey, String emailSender, String emailTemplate) {
        this.emailSender = emailSender;
        this.sendGrid = new SendGrid(apikey);
        this.emailTemplate = emailTemplate;
    }

    public void sendEmailTo(String to, String message, String subject) {
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", message);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.sendGrid.api(request);

        } catch (IOException ex) {
            System.out.println("Error trying to send the email");
        }
    }

    public void welcomeEmail(String email) {
        String subject = "Welcome ONG-Somos Más";
        String message = this.emailTemplate;

        this.sendEmailTo(email, message, subject);
    }
}
