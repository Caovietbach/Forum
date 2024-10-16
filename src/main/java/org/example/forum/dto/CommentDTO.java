package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private String username;

    private Long postId;

    private String content;

    private Date createdAt;

    private int status;

    public CommentDTO(String username, Long postId, String content, Date createdAt, int status) {
        this.username = username;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
    }
}
