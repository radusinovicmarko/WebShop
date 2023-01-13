package org.unibl.etf.ip.webshop.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.webshop.models.dto.CategoryChildrenDTO;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService service;
    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }
    @GetMapping
    public List<CategoryChildrenDTO> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}/sub")
    public List<CategoryDTO> findAllSub(@PathVariable Integer id) {
        return service.findAllChildren(id);
    }
}
