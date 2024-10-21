package org.example.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private Long id;

    private String username;

    private String title;

    private Date createdAt;

    private int status;

    private int likeCount;

    private int dislikeCount;


}
