package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.models.dto.*;
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
    @PostMapping("/login")
    public ResponseEntity<ILoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        ILoginResponseDTO response = authService.login(request);
        if (response instanceof UserDTO)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
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
