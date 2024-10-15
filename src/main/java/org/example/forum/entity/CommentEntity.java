package org.example.forum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.Date;

@Entity
@Data
public class CommentEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;

    private long postId;

    private String content;

    private Date createdAt;

    private int status;
    //status:
    //1:normal
    //2:hidden

}
