package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByUser_Id(Integer id);
}