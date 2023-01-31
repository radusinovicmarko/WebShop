package org.unibl.etf.ip.webshop.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    private final String SENDER = "ip.webshop1@gmail.com";
    private final String TITLE = "Dobrodošli na IP WebShop";
    private final JavaMailSender mailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Async
    @Override
    public void sendEmail(String username, String mail, String pin) {
        String text = "Pozdrav " + username + "! Vaš kod za aktivaciju naloga na IP WebShop aplikaciji je " + pin + ".";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(SENDER);
        mailMessage.setTo(mail);
        mailMessage.setSubject(TITLE);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
        Logger.getLogger(getClass()).info("Activation e-mail sent to mail: " + mail + " of user: " + username);
    }
}
