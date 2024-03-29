package org.unibl.etf.ip.webshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.unibl.etf.ip.webshop.models.dto.*;

public interface ProductService {
    Page<ProductDTO> findAll(Pageable page);
    Page<ProductDTO> findAllByTitle(Pageable page, String title);
    ProductDTO findById(Integer id);
    Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId);
    Page<ProductDTO> findAllByAttributes(Pageable page, String filters);
    ProductDTO insert(NewProductDTO request, Authentication authentication);
    ProductDTO buy(Integer id, PurchaseDTO purchaseDTO, Authentication authentication);
    CommentDTO addComment(CommentRequestDTO comment, Authentication authentication);
    ProductDTO delete(Integer id, Authentication authentication);
}
