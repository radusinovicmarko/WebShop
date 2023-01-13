package org.unibl.etf.ip.webshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.models.dto.CategoryChildrenDTO;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;
import org.unibl.etf.ip.webshop.repositories.CategoryRepository;
import org.unibl.etf.ip.webshop.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final ModelMapper mapper;
    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDTO> findAllChildren(Integer id) {
        List<CategoryEntity> e = repository.findAllChildren(id);
        return e.stream().map(c -> mapper.map(c, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CategoryChildrenDTO> findAll() {
        return repository.findAllByParentCategoryIsNull().stream().map(c -> mapper.map(c, CategoryChildrenDTO.class)).collect(Collectors.toList());
    }
}
