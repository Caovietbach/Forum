package org.example.forum.entity.request;


import lombok.Data;

@Data
public class CommentRequest {
    private Long accountId;

    private Long postId;

    private String content;
}
