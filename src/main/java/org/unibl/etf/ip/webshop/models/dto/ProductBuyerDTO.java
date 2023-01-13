package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductBuyerDTO extends ProductDTO {
    private UserEntity buyer;
}
