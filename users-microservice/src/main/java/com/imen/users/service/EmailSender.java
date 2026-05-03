package com.imen.users.service;

public interface EmailSender {
    void sendEmail(String toEmail, String body);
}
