package org.example.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private String username;

    private String title;

    private Date createdAt;

    private int status;

    public PostDTO(String username, String title, Date createdAt, int status) {
        this.username = username;
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
    }
}
