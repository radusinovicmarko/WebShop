package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PurchaseDTO {
    private Timestamp purchaseDate;
    private Integer buyerId;
}
