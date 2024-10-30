package org.example.forum.service;

import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.request.AccountRequest;
import org.example.forum.response.login.UserLoginResponse;
import org.springframework.stereotype.Service;


public interface AuthenticationService {


    AccountEntity extractUser();

    AccountEntity extractUser(String token);


    void register(String username, String password);

    boolean isTokenBlacklisted(String token);

    JwtBlacklist findJwt(String jwt);


    void checkUser(Long postId, Long commentId, Long accountId);

    void logout(String jwtToken);
    UserLoginResponse  login(AccountRequest user);


}
