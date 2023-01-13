package org.unibl.etf.ip.webshop.models.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentRequestDTO {
    private String content;
    private Timestamp dateTime;
    private int userId;
    private int productId;
}
