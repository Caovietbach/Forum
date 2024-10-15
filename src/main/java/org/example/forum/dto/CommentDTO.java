package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private String username;

    private String postTitle;

    private String content;

    private Date createdAt;

    private int status;

    public CommentDTO(String username, String postTitle, String content, Date createdAt, int status) {
        this.username = username;
        this.postTitle = postTitle;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
    }
}
