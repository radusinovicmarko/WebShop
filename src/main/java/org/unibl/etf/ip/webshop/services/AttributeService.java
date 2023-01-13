package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;

import java.util.List;

public interface AttributeService {
    public List<AttributeEntity> findAllByCategory(Integer id);
}
