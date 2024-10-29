package org.example.forum.service;

import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.response.login.UserLoginResponse;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    String generateToken(String username);

    int extractExpiration(String token);

    AccountEntity extractUser();

    AccountEntity extractUser(String token);

    AccountEntity getUserByName(String username);

    boolean validateLogin(String username, String password);

    void register(String username, String password);

    boolean isTokenBlacklisted(String token);

    JwtBlacklist findJwt(String jwt);

    void addJwtToBlackList(String jwt);

    UserLoginResponse getLoginInfo(String token);

    void checkUser(Long postId, Long commentId, Long accountId);




}
