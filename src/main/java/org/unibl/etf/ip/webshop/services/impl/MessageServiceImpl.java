package org.unibl.etf.ip.webshop.services.impl;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.models.dto.JwtUserDTO;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;
import org.unibl.etf.ip.webshop.models.dto.MessageRequestDTO;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;
import org.unibl.etf.ip.webshop.repositories.MessageRepository;
import org.unibl.etf.ip.webshop.services.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public MessageServiceImpl(MessageRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MessageDTO create(MessageRequestDTO request, Authentication authentication) {
        MessageEntity messageEntity = mapper.map(request, MessageEntity.class);
        messageEntity.setId(null);
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        Logger.getLogger(getClass()).info("Message create request from user: " + jwtUser.getUsername());
        return mapper.map(repository.saveAndFlush(messageEntity), MessageDTO.class);
    }
}
