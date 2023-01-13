package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;

@Data
public class UserBaseDTO {
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String email;
    private String contactPhone;
    private String location;
}
