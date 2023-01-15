package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.enums.AttributeType;

@Data
public class AttributeDTO {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private AttributeType type;
}
