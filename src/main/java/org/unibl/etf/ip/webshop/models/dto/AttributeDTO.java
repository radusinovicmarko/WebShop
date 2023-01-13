package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.enums.AttributeType;

@Data
public class AttributeDTO {
    private Integer id;
    private String name;
    private AttributeType type;
}
