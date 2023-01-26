package org.unibl.etf.ip.webshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;

public interface ProductService {
    Page<ProductDTO> findAll(Pageable page);
    Page<ProductDTO> findAllByTitle(Pageable page, String title);
    ProductDTO findById(Integer id);
    Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId);
    Page<ProductDTO> findAllByAttribute(Pageable page, Integer attributeId, String value, String from, String to);
    ProductDTO insert(NewProductDTO request);
    ProductDTO buy(Integer id, PurchaseDTO purchaseDTO, Authentication authentication);
    CommentDTO addComment(CommentRequestDTO comment);
    ProductDTO delete(Integer id, Authentication authentication);
}
