package org.example.forum.dto;


import lombok.Data;

@Data
public class PostInteractionDTO {

    private Long postId;

    private int interactionType;

    private long interactedAccountId;
}
