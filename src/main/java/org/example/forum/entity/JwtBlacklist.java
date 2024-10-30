package org.example.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class JwtBlacklist {

    @Id
    @GeneratedValue
    private long id;
    private String jwt;
    private Long expirationDate;

}
