package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class AccountInfoDTO {

    private Long id;
    private Long accountId;
    private Date dateOfBirth;
    private int gender;
    private String nationality;
    private String phoneNumbers;
    private String email;
    private String description;

}
