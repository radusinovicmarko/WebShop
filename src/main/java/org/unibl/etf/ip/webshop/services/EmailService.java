package org.unibl.etf.ip.webshop.services;

public interface EmailService {
    void sendEmail(String username, String mail, String pin);
}
