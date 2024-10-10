package org.example.forum.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private Long accountId;

    private String title;

    private Date createdAt;

    private int status;
}
