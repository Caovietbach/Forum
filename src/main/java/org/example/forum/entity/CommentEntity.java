package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class CommentEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;

    private long postId;

    private String content;

    private Date createdAt;

    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CommentEntity(){

    }

    public CommentEntity(Long id, Long accountId, Long postId, String content, Date createdAt, int status) {
        this.id = id;
        this.accountId = accountId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
    }
}
