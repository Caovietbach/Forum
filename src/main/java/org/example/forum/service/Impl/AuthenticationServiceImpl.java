package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.entity.AccountEntity;
import org.example.forum.repository.AccountRepository;
import org.example.forum.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AccountRepository repo;

    private final String SECRET_KEY = "secretfortheproject123456789566343535353453890234567435554";


    public Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public AccountEntity getUserByName(String username) {
        return repo.findByusername(username);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        AccountEntity u = repo.findByusername(username);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public int extractExpiration(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(getSecretKey())
                //.setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        int exp = (int) ((expirationTimeMillis - currentTimeMillis) / 1000);

        return exp;
    }

    public AccountEntity extractUser(String token) {
        String user = Jwts.parser()
                .setSigningKey(getSecretKey())
                //.setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        AccountEntity result = repo.findByusername(user);
        return result;
    }

    public String validateLogin(String username, String password) {
        AccountEntity account = repo.findByusername(username);

        if (username == null) {
           return "Please input user name";}
        else if (password == null){
            return "Please input password";}
        else if (account == null){
            return "No user have this user name";
        } else if (account.getPassword() != password ){
            return "Incorrect password";
        } else {
            return null;
        }
    }

    public void register(String username, String password){
        AccountEntity account = new AccountEntity();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("user");
        account.setStatus(1);
        repo.save(account);
    }






}
