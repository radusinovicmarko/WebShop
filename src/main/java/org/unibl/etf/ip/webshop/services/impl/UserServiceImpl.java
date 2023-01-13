package org.unibl.etf.ip.webshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.models.dto.AccountActivationResponseDTO;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.dto.UserRegisterDTO;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.repositories.UserRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AuthService authService;
    private final ModelMapper mapper;
    @Autowired
    public UserServiceImpl(UserRepository repository, AuthService authService, ModelMapper mapper) {
        this.repository = repository;
        this.authService = authService;
        this.mapper = mapper;
    }
    @Override
    public AccountActivationResponseDTO register(UserRegisterDTO request) {
        UserEntity userEntity = mapper.map(request, UserEntity.class);
        // TODO: Throw Exception
        if (repository.existsByUsername(userEntity.getUsername()))
            return null;
        userEntity = repository.saveAndFlush(userEntity);
        authService.addPin(userEntity.getUsername(), userEntity.getEmail());
        return mapper.map(userEntity, AccountActivationResponseDTO.class);
    }

    @Override
    public UserDTO activateAccount(String username) {
        UserEntity user = repository.findByUsername(username);
        user.setActivated(true);
        return mapper.map(repository.saveAndFlush(user), UserDTO.class);
    }
}
