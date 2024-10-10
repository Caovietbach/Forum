package org.example.forum.entity.account;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class AccountInfoEntity {


    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Date dateOfBirth;

    private int gender;

    private String nationality;

    private int phonenumbers;

    private String email;

    private String description;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getPhonenumbers() {
        return phonenumbers;
    }

    public void setPhonenumbers(int phonenumbers) {
        this.phonenumbers = phonenumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountInfoEntity(){

    }

    public AccountInfoEntity(Long id, Long accountId, Date dateOfBirth, int gender, String nationality, int phonenumbers, String email, String description) {
        this.id = id;
        this.accountId = accountId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.phonenumbers = phonenumbers;
        this.email = email;
        this.description = description;
    }
}
