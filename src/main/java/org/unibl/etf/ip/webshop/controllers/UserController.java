package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.dto.UserUpdateDTO;
import org.unibl.etf.ip.webshop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id, @RequestBody UserUpdateDTO request, Authentication authentication) {
        return service.update(id, request, authentication);
    }

    @GetMapping("/{id}/purchases")
    public Page<ProductDTO> findAllPurchases(Pageable page, @PathVariable Integer id, Authentication authentication) {
        return service.findAllPurchases(page, id, authentication);
    }

    @GetMapping("/{id}/products")
    public Page<ProductDTO> findAllProducts(Pageable page, @PathVariable Integer id, Authentication authentication) {
        return service.findAllProducts(page, id, authentication);
    }

    @GetMapping("/{id}/messages")
    public List<MessageDTO> findAllMessages(@PathVariable Integer id) {
        return service.findAllMessages(id);
    }
}
