package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserBaseDTO {
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String avatarUrl;
    @Email
    private String email;
    @NotBlank
    private String contactPhone;
    @NotBlank
    private String location;
}
