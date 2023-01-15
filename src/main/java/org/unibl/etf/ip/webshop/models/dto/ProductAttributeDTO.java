package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;

@Data
public class ProductAttributeDTO {
    @NotNull
    private AttributeDTO attribute;
    @NotBlank
    private String value;
}
