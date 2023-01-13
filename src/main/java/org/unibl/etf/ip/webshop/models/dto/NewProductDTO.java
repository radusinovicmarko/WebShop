package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class NewProductDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private Timestamp publishDate;
    private List<NewPictureDTO> pictures;
    private Integer sellerId;
    private List<ProductAttributeDTO> attributes;
    private Integer categoryId;
}
