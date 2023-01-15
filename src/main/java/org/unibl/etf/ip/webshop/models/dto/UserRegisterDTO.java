package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterDTO extends UserDTO {
    @NotBlank
    private String password;
}
