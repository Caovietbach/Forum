package org.example.forum.entity.request;

import lombok.Data;

@Data
public class PostInteractionRequest {

    private Long postId;

    private int interactionType;

    private long interactedAccountId;
}
