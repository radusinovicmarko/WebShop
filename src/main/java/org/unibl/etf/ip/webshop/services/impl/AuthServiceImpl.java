package org.unibl.etf.ip.webshop.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.repositories.UserRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.EmailService;

import java.util.*;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final HashMap<String, String> pinCodes = new HashMap<>();
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, EmailService emailService,
                           ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void addPin(String username, String mail) {
        Random rnd = new Random();
        String pin = String.format("%04d", rnd.nextInt(10000));
        List<String> codes =  new ArrayList<>(pinCodes.values());
        while (codes.contains(pin)) {
            pin = String.format("%04d", rnd.nextInt(10000));
        }
        pinCodes.put(username, pin);
        sendPinViaMail(username, mail, pin);
    }

    @Override
    public ILoginResponseDTO login(LoginRequestDTO request) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserEntity userEntity = userRepository.findByUsernameAndDeleted(request.getUsername(), false).orElseThrow(UnauthorizedException::new);
            if (userEntity.isActivated()) {
                JwtUserDTO jwtUser = (JwtUserDTO) auth.getPrincipal();
                LoginResponseDTO response = mapper.map(userEntity, LoginResponseDTO.class);
                response.setToken(generateJwt(jwtUser));
                Logger.getLogger(getClass()).info("User: " + jwtUser.getUsername() + " logged in.");
                return response;
            } else {
                addPin(userEntity.getUsername(), userEntity.getEmail());
                return mapper.map(userEntity, AccountActivationResponseDTO.class);
            }
        } catch (Exception exception) {
            throw new UnauthorizedException();
        }
    }

    private String generateJwt(JwtUserDTO user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    @Override
    public boolean activateAccount(AccountActivationRequestDTO request) {
        return pinCodes.containsKey(request.getUsername()) && pinCodes.get(request.getUsername()).equals(request.getPin());
    }

    @Override
    public LoginResponseDTO loginActivate(UserDTO user) {
        LoginResponseDTO loginResponse = mapper.map(user, LoginResponseDTO.class);
        loginResponse.setToken(generateJwt(mapper.map(user, JwtUserDTO.class)));
        Logger.getLogger(getClass()).info("Account activation  request from user: " + user.getUsername());
        return loginResponse;
    }

    private void sendPinViaMail(String username, String mail, String pin) {
        emailService.sendEmail(username, mail, pin);
    }
}
