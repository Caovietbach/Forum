package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private Long accountId;

    private Long postId;

    private String content;

    private Date createdAt;

    private int status;
}
