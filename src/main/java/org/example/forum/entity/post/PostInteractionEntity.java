package org.example.forum.entity.post;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PostInteractionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long postId;

    private int interactionType;

    private long interactedAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public int getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(int interactionType) {
        this.interactionType = interactionType;
    }

    public long getInteractedAccountId() {
        return interactedAccountId;
    }

    public void setInteractedAccountId(long interactedAccountId) {
        this.interactedAccountId = interactedAccountId;
    }

    public PostInteractionEntity(){

    }

    public PostInteractionEntity(Long id, Long postId, int interactionType, long interactedAccountId) {
        this.id = id;
        this.postId = postId;
        this.interactionType = interactionType;
        this.interactedAccountId = interactedAccountId;
    }
}
