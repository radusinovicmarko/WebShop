package org.unibl.etf.ip.webshop.services.impl;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.exceptions.BadRequestException;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.repositories.MessageRepository;
import org.unibl.etf.ip.webshop.repositories.ProductRepository;
import org.unibl.etf.ip.webshop.repositories.UserRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ProductRepository productRepository;
    private final MessageRepository messageRepository;
    private final AuthService authService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository repository, ProductRepository productRepository, MessageRepository messageRepository,
                           AuthService authService, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.messageRepository = messageRepository;
        this.authService = authService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AccountActivationResponseDTO register(UserRegisterDTO request) {
        UserEntity userEntity = mapper.map(request, UserEntity.class);
        if (repository.existsByUsernameAndDeleted(userEntity.getUsername(), false))
            throw new ConflictException();
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = repository.saveAndFlush(userEntity);
        authService.addPin(userEntity.getUsername(), userEntity.getEmail());
        Logger.getLogger(getClass()).info("Register request from user: " + request.getUsername());
        return mapper.map(userEntity, AccountActivationResponseDTO.class);
    }

    @Override
    public UserDTO activateAccount(String username) {
        Optional<UserEntity> user = repository.findByUsernameAndDeleted(username, false);
        if (user.isEmpty())
            throw new UnauthorizedException();
        user.get().setActivated(true);
        return mapper.map(repository.saveAndFlush(user.get()), UserDTO.class);
    }

    @Override
    public LoginResponseDTO findById(Integer id) {
        return mapper.map(repository.findById(id), LoginResponseDTO.class);
    }

    @Override
    public UserDTO update(Integer id, UserUpdateDTO request, Authentication authentication) {
        Optional<UserEntity> userEntity = repository.findById(id);
        if (userEntity.isEmpty())
            throw new BadRequestException();
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        if (!Objects.equals(jwtUser.getId(), userEntity.get().getId()))
            throw new UnauthorizedException();
        if (passwordEncoder.matches(request.getPassword(), userEntity.get().getPassword())) {
            UserEntity user = userEntity.get();
            if (request.getNewPassword() != null)
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setLocation(request.getLocation());
            user.setContactPhone(request.getContactPhone());
            user.setAvatarUrl(request.getAvatarUrl());
            Logger.getLogger(getClass()).info("Profile update request from user: " + jwtUser.getUsername());
            return mapper.map(repository.saveAndFlush(user), UserDTO.class);
        }
        else
            throw new UnauthorizedException();
    }

    @Override
    public Page<ProductDTO> findAllPurchases(Pageable page, Integer id, Authentication authentication) {
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        if (!id.equals(jwtUser.getId()))
            throw new UnauthorizedException();
        return productRepository.findAllByBuyer_Id(page, id).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> findAllProducts(Pageable page, Integer id, Authentication authentication) {
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        if (!id.equals(jwtUser.getId()))
            throw new UnauthorizedException();
        return productRepository.findAllBySeller_Id(page, id).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public List<MessageDTO> findAllMessages(Integer id) {
        return messageRepository.findAllByUser_Id(id).stream().map(m -> mapper.map(m, MessageDTO.class)).collect(Collectors.toList());
    }
}
