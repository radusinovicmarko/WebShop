package org.unibl.etf.ip.webshop.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.exceptions.BadRequestException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.*;
import org.unibl.etf.ip.webshop.models.enums.ProductStatus;
import org.unibl.etf.ip.webshop.repositories.*;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final PictureRepository pictureRepository;
    private final ProductAttributeEntityRepository productAttributeEntityRepository;
    private final ModelMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository,
                              CommentRepository commentRepository, PictureRepository pictureRepository,
                              ProductAttributeEntityRepository productAttributeEntityRepository, ModelMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.pictureRepository = pictureRepository;
        this.productAttributeEntityRepository = productAttributeEntityRepository;
        this.mapper = mapper;
    }
    @Override
    public Page<ProductDTO> findAll(Pageable page) {
        return repository.findAllByStatus(page, ProductStatus.Active).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public ProductDTO findById(Integer id) {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId) {
        List<Integer> categoryIds = new java.util.ArrayList<>(categoryRepository.findAllChildren(categoryId).stream().map(CategoryEntity::getId).toList());
        categoryIds.add(categoryId);
        return repository.findAllByCategories_IdIn(page, categoryIds).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public ProductDTO insert(NewProductDTO request) {
        ProductEntity productEntity = mapper.map(request, ProductEntity.class);
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(BadRequestException::new);
        productEntity.setId(null);
        productEntity.setStatus(ProductStatus.Active);
        productEntity.setNewProduct(true);
        productEntity.setCategories(new ArrayList<>());
        productEntity.getCategories().add(category);
        for (ProductAttributeEntity pa : productEntity.getAttributes())
            pa.setProduct(productEntity);
        category.getProducts().add(productEntity);
        productEntity = repository.saveAndFlush(productEntity);
        for (PictureEntity picture : productEntity.getPictures()) {
            picture.setId(null);
            picture.setProduct(productEntity);
            pictureRepository.saveAndFlush(picture);
        }
        for (ProductAttributeEntity pa : productEntity.getAttributes()) {
            pa.setProduct(productEntity);
            productAttributeEntityRepository.saveAndFlush(pa);
        }
        return mapper.map(productEntity, ProductDTO.class);
    }

    @Override
    public ProductDTO buy(Integer id, PurchaseDTO purchaseDTO) {
        ProductEntity product = repository.findById(id).orElseThrow(NotFoundException::new);
        if (product.getBuyer() != null || Objects.equals(product.getSeller().getId(), purchaseDTO.getBuyerId()))
            throw new BadRequestException();
        product.setBuyer(new UserEntity());
        product.getBuyer().setId(purchaseDTO.getBuyerId());
        product.setPurchaseDate(purchaseDTO.getPurchaseDate());
        product.setStatus(ProductStatus.Sold);
        return mapper.map(repository.saveAndFlush(product), ProductDTO.class);
    }

    @Override
    public CommentDTO addComment(CommentRequestDTO comment) {
        CommentEntity commentEntity = mapper.map(comment, CommentEntity.class);
        commentEntity.setId(null);
        commentEntity = commentRepository.saveAndFlush(commentEntity);
        entityManager.refresh(commentEntity);
        return mapper.map(commentEntity, CommentDTO.class);
    }

    @Override
    public ProductDTO delete(Integer id, Authentication authentication) {
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        ProductEntity product = repository.findById(id).orElseThrow(NotFoundException::new);
        if (!Objects.equals(jwtUser.getId(), product.getSeller().getId()))
            throw new UnauthorizedException();
        product.setStatus(ProductStatus.Inactive);
        return mapper.map(repository.saveAndFlush(product), ProductDTO.class);
    }
}
