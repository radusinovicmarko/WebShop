package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;
import org.unibl.etf.ip.webshop.services.AttributeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
public class AttributeController {
    private final AttributeService service;
    @Autowired
    public AttributeController(AttributeService service) {
        this.service = service;
    }
    @GetMapping("/category/{id}")
    public List<AttributeEntity> findAllByCategory(@PathVariable Integer id) {
        return service.findAllByCategory(id);
    }
}
