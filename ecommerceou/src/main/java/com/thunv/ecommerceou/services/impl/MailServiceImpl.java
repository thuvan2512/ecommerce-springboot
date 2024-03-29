package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.services.MailService;
import com.thunv.ecommerceou.utils.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    @Autowired
    ThymeleafUtils thymeleafUtils;
    @Autowired
    private Environment env;

    @Override
    @Async
    public void sendMail(String mailTo,String subject,String title,String content,String item, String mailTemplate) {
        try {
            System.out.printf("Async info: thread name = %s\n", Thread.currentThread().getName());
            long start = System.currentTimeMillis();
            String isSendMail = this.env.getProperty("config.mail.disableSendMail");
            if (Integer.parseInt(isSendMail) == 0){
                Properties props = new Properties();
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", port);

                Session session = Session.getInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(email, password);
                            }
                        });
                Message message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(mailTo)});

                message.setFrom(new InternetAddress(email));
                message.setSubject(subject);
                message.setContent(thymeleafUtils.getContent(title,content,item,mailTemplate), CONTENT_TYPE_TEXT_HTML);
                Transport.send(message);
                long end = System.currentTimeMillis();
                long timeExc = end - start;
                System.out.printf("Execute successfully in %s millisecond(s): thread name = %s\n",timeExc , Thread.currentThread().getName());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
