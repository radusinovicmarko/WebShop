package org.unibl.etf.ip.webshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, EmailService emailService, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void addPin(String username, String mail) {
        Random rnd = new Random();
        String pin = String.format("%04d", rnd.nextInt(10000));
        List<String> codes =  new ArrayList<>(pinCodes.values());
        // TODO: Check if necessary
        while (codes.contains(pin)) {
            pin = String.format("%04d", rnd.nextInt(10000));
        }
        pinCodes.put(username, pin);
        sendPinViaMail(username, mail, pin);
    }

    @Override
    public ILoginResponseDTO login(LoginRequestDTO request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity.isEmpty())
            throw new UnauthorizedException();
        if (passwordEncoder.matches(request.getPassword(), userEntity.get().getPassword())) {
            if (userEntity.get().isActivated())
                return mapper.map(userEntity.get(), UserDTO.class);
            else {
                addPin(userEntity.get().getUsername(), userEntity.get().getEmail());
                return mapper.map(userEntity.get(), AccountActivationResponseDTO.class);
            }
        } else
            throw new UnauthorizedException();
    }

    @Override
    public boolean activateAccount(AccountActivationRequestDTO request) {
        return pinCodes.containsKey(request.getUsername()) && pinCodes.get(request.getUsername()).equals(request.getPin());
    }

    private void sendPinViaMail(String username, String mail, String pin) {
        emailService.sendEmail(username, mail, pin);
    }
}
