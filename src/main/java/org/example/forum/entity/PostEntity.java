package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.Date;

@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private String title;

    private Date createdAt;

    private int status;

}
