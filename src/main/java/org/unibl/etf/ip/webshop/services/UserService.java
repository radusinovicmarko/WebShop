package org.unibl.etf.ip.webshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.unibl.etf.ip.webshop.models.dto.*;

import java.util.List;

public interface UserService {
    AccountActivationResponseDTO register(UserRegisterDTO request);
    UserDTO activateAccount(String username);
    UserDTO update(Integer id, UserUpdateDTO request, Authentication authentication);
    Page<ProductDTO> findAllPurchases(Pageable page, Integer id, Authentication authentication);
    Page<ProductDTO> findAllProducts(Pageable page, Integer id, Authentication authentication);
    List<MessageDTO> findAllMessages(Integer id);
}
