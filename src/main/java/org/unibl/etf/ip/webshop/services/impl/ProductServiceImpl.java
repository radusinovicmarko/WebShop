package org.unibl.etf.ip.webshop.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.models.dto.CommentRequestDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;
import org.unibl.etf.ip.webshop.repositories.CategoryRepository;
import org.unibl.etf.ip.webshop.repositories.CommentRepository;
import org.unibl.etf.ip.webshop.repositories.ProductRepository;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.util.List;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }
    @Override
    public Page<ProductDTO> findAll(Pageable page) {
        return repository.findAll(page).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public ProductDTO findById(Integer id) {
        return mapper.map(repository.findById(id), ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId) {
        List<Integer> categoryIds = new java.util.ArrayList<>(categoryRepository.findAllChildren(categoryId).stream().map(CategoryEntity::getId).toList());
        categoryIds.add(categoryId);
        return repository.findAllByCategories_IdIn(page, categoryIds).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> findAllByBuyer(Pageable page, Integer buyerId) {
        return repository.findAllByBuyer_Id(page, buyerId).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public CommentEntity insert(CommentRequestDTO comment) {
        CommentEntity entity = commentRepository.saveAndFlush(mapper.map(comment, CommentEntity.class));
        entityManager.refresh(entity);
        return entity;
    }
}
