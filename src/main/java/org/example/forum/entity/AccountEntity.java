package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AccountEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String role;


    private Date createdAt;

    private int status;
    //status:
    //1:normal
    //2:mute

}
