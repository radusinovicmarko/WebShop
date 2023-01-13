package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterDTO extends UserBaseDTO {
    private String username;
    private String password;
}
