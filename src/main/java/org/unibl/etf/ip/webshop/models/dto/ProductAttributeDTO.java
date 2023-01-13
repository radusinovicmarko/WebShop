package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;

@Data
public class ProductAttributeDTO {
    private AttributeDTO attribute;
    private String value;
}
