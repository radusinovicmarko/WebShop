package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.AccountActivationResponseDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.dto.UserRegisterDTO;

public interface UserService {
    AccountActivationResponseDTO register(UserRegisterDTO request);
    UserDTO activateAccount(String username);
}
