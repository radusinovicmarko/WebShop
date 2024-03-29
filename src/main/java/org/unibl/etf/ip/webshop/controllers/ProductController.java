package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    public Page<ProductDTO> findAll(Pageable page, @RequestParam(required = false) String title) {
        if (title != null)
            return service.findAllByTitle(page, title);
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

    @GetMapping("/attributes")
    public Page<ProductDTO> findAllByAttributes(Pageable page, @RequestParam(required = false) String filters) {
        return service.findAllByAttributes(page, filters);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO insert(@RequestBody @Valid NewProductDTO request, Authentication authentication) {
        return service.insert(request, authentication);
    }

    @PostMapping("/{id}/purchase")
    public ProductDTO buy(@PathVariable Integer id, @RequestBody @Valid PurchaseDTO purchaseDTO, Authentication authentication) {
        return service.buy(id, purchaseDTO, authentication);
    }

    @DeleteMapping("/{id}")
    public ProductDTO delete(@PathVariable Integer id, Authentication authentication) {
        return service.delete(id, authentication);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody @Valid CommentRequestDTO comment, @PathVariable Integer id, Authentication authentication) {
        comment.setProductId(id);
        return service.addComment(comment, authentication);
    }
}
