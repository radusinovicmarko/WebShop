package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private Integer id;
    private String content;
    private Timestamp dateTime;
    private UserDTO user;
}
