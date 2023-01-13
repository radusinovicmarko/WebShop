package org.unibl.etf.ip.webshop.models.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductAttributeKey implements Serializable {
    private Integer attribute;
    private Integer product;
}
