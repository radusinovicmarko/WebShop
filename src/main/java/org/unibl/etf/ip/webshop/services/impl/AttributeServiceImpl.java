package org.unibl.etf.ip.webshop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;
import org.unibl.etf.ip.webshop.repositories.AttributeRepository;
import org.unibl.etf.ip.webshop.services.AttributeService;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository repository;
    @Autowired
    public AttributeServiceImpl(AttributeRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<AttributeEntity> findAllByCategory(Integer id) {
        return repository.findAllByCategory_Id(id);
    }
}
