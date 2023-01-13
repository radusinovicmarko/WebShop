package org.unibl.etf.ip.webshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.unibl.etf.ip.webshop.models.dto.CommentRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;

public interface ProductService {
    public Page<ProductDTO> findAll(Pageable page);
    public ProductDTO findById(Integer id);
    public Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId);
    public Page<ProductDTO> findAllByBuyer(Pageable page, Integer buyerId);
    public CommentEntity insert(CommentRequestDTO comment);
}
