package com.example.Api.inheritance;

import jakarta.mail.MessagingException;

public interface ISmtp {
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException;
}
