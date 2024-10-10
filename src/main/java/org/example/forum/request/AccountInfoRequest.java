package org.example.forum.request;

import lombok.Data;

import java.util.Date;

@Data
public class AccountInfoRequest {

    private Long accountId;
    private Date dateOfBirth;
    private int gender;
    private String nationality;
    private String phoneNumbers;
    private String email;
    private String description;

}