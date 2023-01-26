package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
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
    public AccountActivationResponseDTO register(@RequestBody @Valid UserRegisterDTO request) {
        return userService.register(request);
    }
    @PostMapping("/login")
    public ILoginResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authService.login(request);
    }
    @GetMapping("/state")
    public LoginResponseDTO state(Authentication authentication) {
        if (authentication == null)
            throw new UnauthorizedException();
        return userService.findById(((JwtUserDTO) authentication.getPrincipal()).getId());
    }
    @PostMapping("/activate")
    public LoginResponseDTO activateAccount(@RequestBody @Valid AccountActivationRequestDTO request) {
        if (authService.activateAccount(request)) {
            UserDTO user = userService.activateAccount(request.getUsername());
            return authService.loginActivate(user);
        } else {
            throw new UnauthorizedException();
        }
    }
}
