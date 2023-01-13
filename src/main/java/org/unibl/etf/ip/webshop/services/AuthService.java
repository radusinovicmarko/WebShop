package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.AccountActivationRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;

public interface AuthService {
    void addPin(String username, String mail);

    public boolean activateAccount(AccountActivationRequestDTO request);
}
