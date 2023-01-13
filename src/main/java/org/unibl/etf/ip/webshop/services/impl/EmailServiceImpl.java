package org.unibl.etf.ip.webshop.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    // private final JavaMailSender
    private final String SENDER = "radusinovicmarko12@gmail.com";
    private final String TITLE = "Dobrodošli na IP WebShop";
    @Override
    public void sendEmail(String username, String mail, String pin) {

    }
}
