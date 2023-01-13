package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.models.dto.CommentRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;
import org.unibl.etf.ip.webshop.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public Page<ProductDTO> findAll(Pageable page) {
        return service.findAll(page);
    }
    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }
    @GetMapping("/category/{id}")
    public Page<ProductDTO> findAllByCategory(Pageable page, @PathVariable Integer id) {
        return service.findAllByCategory(page, id);
    }
    @GetMapping("/buyer/{id}")
    public Page<ProductDTO> findAllByBuyer(Pageable page, @PathVariable Integer id) {
        return service.findAllByBuyer(page, id);
    }
    @PostMapping("/{id}/comments")
    public CommentEntity addComment(@RequestBody CommentRequestDTO comment, @PathVariable Integer id) {
        comment.setProductId(id);
        return service.insert(comment);
    }
}
