package com.stefano.application.services;

public interface MailService {
    void enviarCorreo(String email, String subject, String text);
}
