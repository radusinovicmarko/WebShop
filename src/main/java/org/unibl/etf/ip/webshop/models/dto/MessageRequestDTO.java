package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

@Data
public class MessageRequestDTO {
    private String title;
    private String content;
    private int userId;
}
