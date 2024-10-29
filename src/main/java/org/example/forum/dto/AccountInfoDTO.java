package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class AccountInfoDTO {

    private String username;
    private Date dateOfBirth;
    private String gender;
    private String nationality;
    private String phoneNumbers;
    private String email;
    private String description;

}
