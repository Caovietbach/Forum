package org.example.forum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class AccountInfoDTO {

    private String username;
    private Date dateOfBirth;
    private int gender;
    private String nationality;
    private String phoneNumbers;
    private String email;
    private String description;

    public AccountInfoDTO(String username, Date dateOfBirth, int gender, String nationality, String phoneNumbers, String email, String description) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.phoneNumbers = phoneNumbers;
        this.email = email;
        this.description = description;
    }
}
