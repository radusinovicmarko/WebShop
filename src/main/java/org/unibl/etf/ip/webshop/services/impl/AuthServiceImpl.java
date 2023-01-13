package org.unibl.etf.ip.webshop.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.models.dto.AccountActivationRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.services.AuthService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final HashMap<String, String> pinCodes = new HashMap<>();

    public void addPin(String username, String mail) {
        Random rnd = new Random();
        String pin = String.format("%04d", rnd.nextInt(10000));
        List<String> codes =  new ArrayList<>(pinCodes.values());
        // TODO: Check if necessary
        while (codes.contains(pin)) {
            pin = String.format("%04d", rnd.nextInt(10000));
        }
        pinCodes.put(username, pin);
        sendPinViaMail(mail, pin);
    }

    @Override
    public boolean activateAccount(AccountActivationRequestDTO request) {
        return pinCodes.containsKey(request.getUsername()) && pinCodes.get(request.getUsername()).equals(request.getPin());
    }

    // TODO: Send mail
    private void sendPinViaMail(String mail, String pin) {
        System.out.println(mail + " : " + pin);
    }
}
