package com.thunv.ecommerceou.services;

public interface MailService {
    void sendMail(String mailTo,String subject,String title,String content,String items, String mailTemplate);
}
