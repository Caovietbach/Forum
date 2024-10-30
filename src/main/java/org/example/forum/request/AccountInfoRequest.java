package org.example.forum.request;

import lombok.Data;

import java.util.Date;

@Data
public class AccountInfoRequest {

    private Long accountId;
    private Date dateOfBirth;
    private int gender;
    private String nationality;
    private int phoneNumbers;
    private String email;
    private String description;

}