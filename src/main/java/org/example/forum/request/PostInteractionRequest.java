package org.example.forum.request;

import lombok.Data;

@Data
public class PostInteractionRequest {

    private Long postId;

    private int interactionType;

    private long interactedAccountId;
}
