package org.unibl.etf.ip.webshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.JwtUserDTO;
import org.unibl.etf.ip.webshop.repositories.UserRepository;
import org.unibl.etf.ip.webshop.services.JwtUserDetailsService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public JwtUserDetailsServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapper.map(userRepository.findByUsername(username).orElseThrow(UnauthorizedException::new), JwtUserDTO.class);
    }
}
