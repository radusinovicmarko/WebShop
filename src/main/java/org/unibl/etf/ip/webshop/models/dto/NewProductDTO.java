package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class NewProductDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String location;
    @NotNull
    private Timestamp publishDate;
    private List<NewPictureDTO> pictures;
    @NotNull
    private Integer sellerId;
    @NotEmpty
    private List<ProductAttributeDTO> attributes;
    @NotNull
    private Integer categoryId;
}
