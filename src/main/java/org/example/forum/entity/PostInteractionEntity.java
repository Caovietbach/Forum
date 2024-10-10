package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PostInteractionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long postId;

    private int interactionType;

    private long interactedAccountId;


}
