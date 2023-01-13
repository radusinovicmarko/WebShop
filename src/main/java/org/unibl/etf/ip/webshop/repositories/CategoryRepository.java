package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query("select c from CategoryEntity c where c.parentCategory=null")
    public List<CategoryEntity> findAllByParentCategoryIsNull();
    @Query("select s from CategoryEntity c inner join CategoryEntity s on c.id=s.parentCategory.id where c.id=:id")
    public List<CategoryEntity> findAllChildren(@Param("id") Integer id);
}