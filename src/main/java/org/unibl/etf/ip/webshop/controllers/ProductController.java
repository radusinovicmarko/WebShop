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

    /*@GetMapping("/attribute/{id}")
    public Page<ProductDTO> findAllByAttribute(Pageable page, @PathVariable Integer id, @RequestParam(required = false) String value,
                                               @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        return service.findAllByAttribute(page, id, value, from, to);
    }*/
    @GetMapping("/attributes")
    public Page<ProductDTO> findAllByAttributes(Pageable page, @RequestParam(required = false) String filters) {
        return service.findAllByAttributes(page, filters);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO insert(@RequestBody @Valid NewProductDTO request) {
        return service.insert(request);
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
    public CommentDTO addComment(@RequestBody @Valid CommentRequestDTO comment, @PathVariable Integer id) {
        comment.setProductId(id);
        return service.addComment(comment);
    }
}
