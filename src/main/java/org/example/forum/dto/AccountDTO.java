package org.example.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDTO {

    private String username;
    private String role;
    private Date createdAt;
    private int status;

    public AccountDTO( String username, String role, Date createdAt, int status) {
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
        this.status = status;
    }
}
