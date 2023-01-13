package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserDTO update(@PathVariable Integer id, @RequestBody UserUpdateDTO request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}/purchases")
    public Page<ProductDTO> findAllPurchases(Pageable page, @PathVariable Integer id) {
        return service.findAllPurchases(page, id);
    }

    @GetMapping("/{id}/products")
    public Page<ProductDTO> findAllProducts(Pageable page, @PathVariable Integer id) {
        return service.findAllProducts(page, id);
    }

    @GetMapping("/{id}/messages")
    public List<MessageDTO> findAllMessages(@PathVariable Integer id) {
        return service.findAllMessages(id);
    }
}
