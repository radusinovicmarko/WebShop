package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;

public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Integer> {
}