package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryChildrenDTO extends CategoryDTO {
    private List<CategoryEntity> subcategories;
}
