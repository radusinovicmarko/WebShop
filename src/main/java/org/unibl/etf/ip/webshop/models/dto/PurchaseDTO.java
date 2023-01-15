package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PurchaseDTO {
    @NotNull
    private Timestamp purchaseDate;
    @NotNull
    private Integer buyerId;
}
