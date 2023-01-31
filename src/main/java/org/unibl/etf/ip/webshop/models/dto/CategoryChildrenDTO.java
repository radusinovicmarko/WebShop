package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryChildrenDTO extends CategoryDTO {
    private List<CategoryDTO> subcategories;
}
