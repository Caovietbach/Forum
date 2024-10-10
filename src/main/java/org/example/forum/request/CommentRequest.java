package org.example.forum.request;


import lombok.Data;

@Data
public class CommentRequest {
    private Long accountId;

    private Long postId;

    private String content;
}
