package org.unibl.etf.ip.webshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;

public interface ProductService {
    Page<ProductDTO> findAll(Pageable page);
    ProductDTO findById(Integer id);
    Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId);
    ProductDTO insert(NewProductDTO request);
    ProductDTO buy(Integer id, PurchaseDTO purchaseDTO);
    CommentDTO addComment(CommentRequestDTO comment);
    ProductDTO delete(Integer id);
}
