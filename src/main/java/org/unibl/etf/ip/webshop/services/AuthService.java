package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.*;

public interface AuthService {
    void addPin(String username, String mail);
    ILoginResponseDTO login(LoginRequestDTO request);
    boolean activateAccount(AccountActivationRequestDTO request);
    LoginResponseDTO loginActivate(UserDTO user);
}
