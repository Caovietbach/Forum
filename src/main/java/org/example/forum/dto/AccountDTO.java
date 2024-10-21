package org.example.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDTO {

    private String username;
    private String role;
    private Date createdAt;
    private int status;

}
