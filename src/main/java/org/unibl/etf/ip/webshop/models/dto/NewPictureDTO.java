package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewPictureDTO {
    @NotBlank
    private String pictureUrl;
}
