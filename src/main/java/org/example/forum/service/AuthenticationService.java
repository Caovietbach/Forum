package org.example.forum.service;

import org.example.forum.entity.AccountEntity;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    String generateToken(String username);

    int extractExpiration(String token);

    AccountEntity extractUser(String token);

    AccountEntity getUserByName(String username);

    String validateLogin(String username, String password);

    void register(String username, String password);
}
