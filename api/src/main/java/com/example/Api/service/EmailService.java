package com.example.Api.service;

import com.example.Api.inheritance.IEmail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final IEmail emailAdapter;

    @Autowired
    public EmailService(IEmail emailAdapter) {
        this.emailAdapter = emailAdapter;
    }

    public void sendInvoice(String toEmail, String subject, String body) {
        emailAdapter.sendInvoiceEmail(toEmail, subject, body);
    }

    public void sendSimple(String to, String subject, String text) {
        emailAdapter.sendSimpleEmail(to, subject, text);
    }

    public void notifyEmailChange(String recipientEmail, String code) throws MessagingException {
        emailAdapter.sendEmailChangeNotification(recipientEmail, code);
    }
}
