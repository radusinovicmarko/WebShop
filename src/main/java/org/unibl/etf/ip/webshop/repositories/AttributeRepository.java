package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;

import java.util.List;

public interface AttributeRepository extends JpaRepository<AttributeEntity, Integer> {
    public List<AttributeEntity> findAllByCategory_Id(Integer id);
}