package com.example.Api.inheritance;

public interface IEmail {
    void sendInvoiceEmail(String toEmail, String subject, String body);
    void sendSimpleEmail(String to, String subject, String text);
    void sendEmailChangeNotification(String recipientEmail, String code);
}
