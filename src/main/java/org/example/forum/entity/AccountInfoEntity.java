package org.example.forum.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AccountInfoEntity {


    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Date dateOfBirth;

    private int gender;

    private String nationality;

    private int phoneNumbers;

    private String email;

    private String description;
}
