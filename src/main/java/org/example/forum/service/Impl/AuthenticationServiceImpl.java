package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.AccountInfoEntity;
import org.example.forum.entity.CommentEntity;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.exception.ValidateException;
import org.example.forum.repository.AccountInfoRepository;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.JwtBlacklistRepository;
import org.example.forum.request.AccountRequest;
import org.example.forum.response.login.UserLoginResponse;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.CommentService;
import org.example.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
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

import static org.example.forum.constants.AppConstants.DELETION_DATE;
import static org.example.forum.constants.AppConstants.SECRET_KEY;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Lazy
    @Autowired
    private PostService postService;

    @Lazy
    @Autowired
    private CommentService commentService;




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


    public int calculateExpirationDate(String token) {
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

    private Long extractExpirationDate(String token){
        Date expiration = Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        Long expirationTimeMillis = expiration.getTime();
        return expirationTimeMillis;
    }

    public AccountEntity extractUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("the authen: {}", authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AccountEntity) {
                return (AccountEntity) principal;
            }
        } else {
            throw new ValidateException("Please login to use this function");
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
        if (result == null){
            throw new ValidateException("Please login to use this function");
        }
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
        a.setExpirationDate(extractExpirationDate(jwt));
        save(a);
    }

    public UserLoginResponse getLoginInfo(String token){
        UserLoginResponse res = new UserLoginResponse();
        res.setAccessToken(token);
        res.setTokenType("Bearer");
        res.setExpiresIn(calculateExpirationDate(token));
        if (token == null) {
            throw new ValidateException("Invalid token");
        }
        return res;
    }

    public void checkUser(Long postId, Long commentId, Long accountId){
        Long id = null;
        if (postId != null){
            id = postService.findPostById(postId).getAccountId();
        } else if (commentId != null){
            id= commentService.findCommentById(commentId).getAccountId();
        } else if (accountId != null){
            id = accountId;
        } else {
            throw new ValidateException("Something is wrong in the checking user process.");
        }
        AccountEntity currentAccount = extractUser();
        if(!currentAccount.getId().equals(id)){
            throw new ValidateException("This is from another account, you can't do this function");
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void deleteExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        long deletionDate = currentTime + DELETION_DATE;
        jwtBlacklistRepository.deleteAllExpiredJWT(deletionDate);
        logger.info("Expired JWT tokens deleted at: {}", new Date(currentTime));
    }

    /////////////////////////////////////////////////////
    //Functions that handle the authentication process///
    /////////////////////////////////////////////////////
    public UserLoginResponse login(AccountRequest user){
        validateLogin(user.getUsername(), user.getPassword());
        String token = generateToken(user.getUsername());
        logger.info("The new token is:{}",token);
        UserLoginResponse res = getLoginInfo(token);
        return res;
    }

    public void register(String username, String password){
        AccountEntity account = new AccountEntity();
        Date d = new Date(System.currentTimeMillis());
        account.setUsername(username);
        account.setPassword(password);
        account.setCreatedAt(d);
        account.setRole("user");
        account.setStatus(1);
        accountRepository.save(account);
        AccountInfoEntity aie = new AccountInfoEntity();
        aie.setAccountId(account.getId());
        accountInfoRepository.save(aie);
    }

    public void logout(String jwtToken){
        JwtBlacklist jwtBlacklist = findJwt(jwtToken);
        if (jwtBlacklist == null) {
            addJwtToBlackList(jwtToken);
        }
    }





}
