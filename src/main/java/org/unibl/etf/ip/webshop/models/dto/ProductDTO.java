package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;
import org.unibl.etf.ip.webshop.models.entities.PictureEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductAttributeEntity;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.models.enums.ProductStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ProductDTO {@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private boolean newProduct;
    private String location;
    private ProductStatus status;
    private Timestamp publishDate;
    private Timestamp purchaseDate;
    private List<CommentDTO> comments;
    private List<PictureDTO> pictures;
    private UserDTO seller;
    private UserDTO buyer;
    private List<ProductAttributeDTO> attributes;
}
