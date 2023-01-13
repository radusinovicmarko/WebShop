package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.AccountActivationRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.ILoginResponseDTO;
import org.unibl.etf.ip.webshop.models.dto.LoginRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;

public interface AuthService {
    void addPin(String username, String mail);
    ILoginResponseDTO login(LoginRequestDTO request);
    boolean activateAccount(AccountActivationRequestDTO request);
}
