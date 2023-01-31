package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttributeSearchDTO {
    private Integer id;
    private String value;
    private String from;
    private String to;
}
