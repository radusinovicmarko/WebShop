package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends UserBaseDTO implements ILoginResponseDTO {
    @NotBlank
    private String username;
}
