package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class PostInteractionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long postId;

    private int interactionType;
    //Like: 1
    //Dislike: 2

    private long interactedAccountId;


}
