package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.MessageDTO;
import org.unibl.etf.ip.webshop.models.dto.MessageRequestDTO;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;

import java.util.List;

public interface MessageService {
    MessageDTO create(MessageRequestDTO request);
}
