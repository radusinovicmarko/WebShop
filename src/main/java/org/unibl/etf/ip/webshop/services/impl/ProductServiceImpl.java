package org.unibl.etf.ip.webshop.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public Page<ProductDTO> findAllByTitle(Pageable page, String title) {
        return repository.findAllByStatusAndTitleContainingIgnoreCase(page, ProductStatus.Active, title).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public ProductDTO findById(Integer id) {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> findAllByCategory(Pageable page, Integer categoryId) {
        List<Integer> categoryIds = new java.util.ArrayList<>(categoryRepository.findAllChildren(categoryId).stream().map(CategoryEntity::getId).toList());
        categoryIds.add(categoryId);
        return repository.findAllByCategories_IdInAndStatus(page, categoryIds, ProductStatus.Active).map(p -> mapper.map(p, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> findAllByAttributes(Pageable page, String filters) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AttributeSearchDTO> searchFilters;
        try {
            String filtersDecoded = URLDecoder.decode(filters, StandardCharsets.UTF_16);
            System.out.println(filtersDecoded);
            searchFilters = objectMapper.readValue(filtersDecoded, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new BadRequestException();
        }
        List<Integer> ids = searchFilters.stream().map(AttributeSearchDTO::getId).toList();
        List<ProductEntity> filteredProducts = repository.findAllByAttributes_Attribute_IdInAndStatus(ids, ProductStatus.Active);
        for (AttributeSearchDTO filter : searchFilters) {
            String value = filter.getValue();
            String from = filter.getFrom();
            String to = filter.getTo();
            Integer attributeId = filter.getId();
            if (value.isEmpty() && from.isEmpty() && to.isEmpty())
                throw new BadRequestException();
            if (!value.isEmpty()) {
                if (!from.isEmpty() || !to.isEmpty())
                    throw new BadRequestException();
                filteredProducts = filteredProducts.stream().filter(p -> {
                    for (ProductAttributeEntity pa : p.getAttributes())
                        if (pa.getAttribute().getId().equals(attributeId) && pa.getValue().toLowerCase().contains(value.toLowerCase()))
                            return true;
                    return false;
                }).toList();
            } else {
                if (from.isEmpty() || to.isEmpty())
                    throw new BadRequestException();
                double fromDouble, toDouble;
                try {
                    fromDouble = Double.parseDouble(from);
                    toDouble = Double.parseDouble(to);
                } catch (NumberFormatException exception) {
                    throw new BadRequestException();
                }
                filteredProducts = filteredProducts.stream().filter(p -> {
                    for (ProductAttributeEntity pa : p.getAttributes())
                        if (pa.getAttribute().getId().equals(attributeId) && Double.parseDouble(pa.getValue()) >= fromDouble && Double.parseDouble(pa.getValue()) <= toDouble)
                            return true;
                    return false;
                }).toList();
            }
        }
        List<ProductDTO> filteredProductsDTO = filteredProducts.stream().map(p -> mapper.map(p, ProductDTO.class)).toList();
        return new PageImpl<>(filteredProductsDTO, page, filteredProducts.size());
    }

    @Override
    public ProductDTO insert(NewProductDTO request) {
        ProductEntity productEntity = mapper.map(request, ProductEntity.class);
        productEntity.setId(null);
        productEntity.setStatus(ProductStatus.Active);
        productEntity.setNewProduct(true);
        productEntity.setCategories(new ArrayList<>());
        for (Integer id : request.getCategoryIds()) {
            CategoryEntity category = categoryRepository.findById(id).orElseThrow(BadRequestException::new);
            productEntity.getCategories().add(category);
            category.getProducts().add(productEntity);
        }
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
    public ProductDTO buy(Integer id, PurchaseDTO purchaseDTO, Authentication authentication) {
        ProductEntity product = repository.findById(id).orElseThrow(NotFoundException::new);
        if (product.getBuyer() != null || Objects.equals(product.getSeller().getId(), purchaseDTO.getBuyerId()))
            throw new BadRequestException();
        if (product.getStatus() != ProductStatus.Active)
            throw new BadRequestException();
        JwtUserDTO jwtUser = (JwtUserDTO) authentication.getPrincipal();
        if (jwtUser.getId().equals(product.getSeller().getId()))
            throw new UnauthorizedException();
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
        if (product.getStatus() != ProductStatus.Active)
            throw new BadRequestException();
        product.setStatus(ProductStatus.Inactive);
        return mapper.map(repository.saveAndFlush(product), ProductDTO.class);
    }
}
