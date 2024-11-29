package org.example.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class JwtBlacklist {

    @Id
    @GeneratedValue
    private long id;
    private String jwt;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public JwtBlacklist(){

    }

    public JwtBlacklist(long id, String jwt) {
        this.id = id;
        this.jwt = jwt;
    }
}
