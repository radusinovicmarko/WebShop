package org.unibl.etf.ip.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.models.dto.*;
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

    @PostMapping
    public ProductDTO insert(@RequestBody NewProductDTO request) {
        return service.insert(request);
    }

    // TODO: update product endpoint?

    @PostMapping("/{id}/purchase")
    public ProductDTO buy(@PathVariable Integer id, @RequestBody PurchaseDTO purchaseDTO) {
        return service.buy(id, purchaseDTO);
    }

    @DeleteMapping("/{id}")
    public ProductDTO delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@RequestBody CommentRequestDTO comment, @PathVariable Integer id) {
        comment.setProductId(id);
        return service.addComment(comment);
    }
}
