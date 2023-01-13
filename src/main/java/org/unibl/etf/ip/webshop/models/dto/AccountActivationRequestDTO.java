package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountActivationRequestDTO extends AccountActivationResponseDTO {
    private String pin;
}
