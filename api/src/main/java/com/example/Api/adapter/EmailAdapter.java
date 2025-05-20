package com.example.Api.adapter;

import com.example.Api.inheritance.IEmail;
import com.example.Api.adapter.SmtpAdaptee;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class EmailAdapter implements IEmail {

    private final SmtpAdaptee smtpClient;

    public EmailAdapter(SmtpAdaptee smtpClient) {
        this.smtpClient = smtpClient;
    }

    @Override
    public void sendInvoiceEmail(String toEmail, String subject, String body) {
        try {
            smtpClient.sendHtmlEmail(toEmail, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        String html = "<p>" + text + "</p>";
        try {
            smtpClient.sendHtmlEmail(to, subject, html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailChangeNotification(String recipientEmail, String code) {
        String content = "<p>Mã xác nhận của bạn là: <strong>" + code + "</strong></p>";
        try {
            smtpClient.sendHtmlEmail(recipientEmail, "Thay đổi email Deliveryfood", content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

