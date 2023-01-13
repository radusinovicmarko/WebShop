package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    public Page<ProductEntity> findAllByCategories_IdIn(Pageable page, List<Integer> ids);
    public Page<ProductEntity> findAllByBuyer_Id(Pageable page, Integer id);
}