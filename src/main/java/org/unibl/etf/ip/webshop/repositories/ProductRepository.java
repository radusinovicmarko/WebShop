package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
import org.unibl.etf.ip.webshop.models.enums.ProductStatus;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findAllByStatusAndTitleContainingIgnoreCase(Pageable page, ProductStatus productStatus, String title);
    Page<ProductEntity> findAllByStatus(Pageable page, ProductStatus productStatus);
    Page<ProductEntity> findAllByCategories_IdInAndStatus(Pageable page, List<Integer> ids, ProductStatus productStatus);
    List<ProductEntity> findAllByAttributes_Attribute_IdAndStatus(Integer id, ProductStatus productStatus);
    Page<ProductEntity> findAllByBuyer_Id(Pageable page, Integer id);
    Page<ProductEntity> findAllBySeller_Id(Pageable page, Integer id);
}