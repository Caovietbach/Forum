package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.exception.ValidateException;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.JwtBlacklistRepository;
import org.example.forum.response.login.UserLoginResponse;
import org.example.forum.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.sql.Struct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    private final String SECRET_KEY = "secretfortheproject123456789566343535353453890234567435554";

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    public Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public AccountEntity getUserByName(String username) {
        return accountRepository.findByusername(username);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        AccountEntity u = accountRepository.findByusername(username);
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

    public AccountEntity extractUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("the authen: {}", authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AccountEntity) {
                return (AccountEntity) principal;
            }
        }
        return null;
    }

    public AccountEntity extractUser(String token) {
        String user = Jwts.parser()
                .setSigningKey(getSecretKey())
                //.setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        AccountEntity result = accountRepository.findByusername(user);
        return result;
    }

    public boolean validateLogin(String username, String password) {
        AccountEntity account = accountRepository.findByusername(username);

        if (username == null) {
           throw new ValidateException("Please input user name");}
        else if (password == null){
            throw new ValidateException("Please input password");}
        else if (account == null){
            throw new ValidateException("No user have this user name");
        } else if (!account.getPassword().equals(password) ){
            throw new ValidateException("Incorrect password");
        } else {
            return true;
        }
    }

    public void register(String username, String password){
        AccountEntity account = new AccountEntity();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("user");
        account.setStatus(1);
        accountRepository.save(account);
    }


    public void save(JwtBlacklist jwtBlacklist) {
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    public JwtBlacklist findJwt(String jwtToken) {
        return jwtBlacklistRepository.findByJwt(jwtToken);
    }

    public boolean isTokenBlacklisted(String jwtToken) {
        String token = jwtToken.substring(7);
        if (jwtBlacklistRepository.findByJwt(token) != null){
            return true;
        } else {
            return false;
        }
    }

    public void addJwtToBlackList(String jwtToken){
        String jwt = jwtToken.substring(7);
        JwtBlacklist a = new JwtBlacklist();
        a.setJwt(jwt);
        save(a);
    }

    public UserLoginResponse getLoginInfo(String token){
        UserLoginResponse res = new UserLoginResponse();
        res.setAccessToken(token);
        res.setTokenType("Bearer");
        res.setExpiresIn(extractExpiration(token));
        if (token == null) {
            throw new ValidateException("Invalid token");
        } else {
            return res;
        }

    }






}
