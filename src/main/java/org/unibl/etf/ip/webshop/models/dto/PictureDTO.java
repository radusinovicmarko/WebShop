package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PictureDTO extends NewPictureDTO {
    private Integer id;
}
