package com.example.Api.controller;

import com.example.Api.adapter.EmailAdapter;
import com.example.Api.adapter.SmtpAdaptee;
import com.example.Api.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class ControllerSendEmail {

    @Autowired
    private EmailService emailService;


    @PostMapping("/changeEmail")
    public String sendChangeEmailNotification(@RequestBody Map<String, Object> emailData) {
        String recipientEmail = (String) emailData.get("email");
        String code = (String) emailData.get("code");

        try {
            emailService.notifyEmailChange(recipientEmail, code);
            return "Email thay đổi email đã được gửi thành công!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Có lỗi xảy ra khi gửi email.";
        }
    }
}
