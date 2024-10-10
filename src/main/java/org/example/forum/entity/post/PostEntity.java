package org.example.forum.entity.post;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public PostEntity(){

    }
    public PostEntity(Long id, Long accountId, String title, Date createdAt, int status) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
    }
}
