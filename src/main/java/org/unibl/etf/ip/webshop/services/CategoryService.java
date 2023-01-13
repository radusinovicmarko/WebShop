package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.models.dto.CategoryChildrenDTO;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> findAllChildren(Integer id);
    public List<CategoryChildrenDTO> findAll();
}
