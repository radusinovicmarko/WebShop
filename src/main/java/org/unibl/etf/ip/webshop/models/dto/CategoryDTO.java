package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;

import java.util.List;

@Data
public class CategoryDTO {
    private int id;
    private String name;
    private List<AttributeEntity> attributes;
}
