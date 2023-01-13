package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.models.dto.AccountActivationRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.AccountActivationResponseDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.dto.UserRegisterDTO;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/register")
    public AccountActivationResponseDTO register(@RequestBody UserRegisterDTO request) {
        return userService.register(request);
    }
    @PostMapping("/activate")
    public UserDTO activateAccount(@RequestBody AccountActivationRequestDTO request) {
        if (authService.activateAccount(request)) {
            return userService.activateAccount(request.getUsername());
        } else {
            return null;
        }
    }
}
