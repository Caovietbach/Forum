package org.example.forum.controller;


import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.response.api.ApiResponse;
import org.example.forum.response.login.UserLoginResponse;
import org.example.forum.service.AccountService;
import org.example.forum.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/login")
    public ApiResponse<UserLoginResponse> login(@RequestBody AccountEntity user) {
        service.validateLogin(user.getUsername(),user.getPassword());
        String token = service.generateToken(user.getUsername());
        logger.info("The new token is:{}",token);
        UserLoginResponse res = service.getLoginInfo(token);
        return new ApiResponse<>(true, "Login successfully", res);
    }

    @PostMapping("/logout")
    public ApiResponse<UserLoginResponse> logout(@RequestHeader("Authorization") String jwtToken) {
        logger.info("Token for logout: {}", jwtToken);
        JwtBlacklist jwtBlacklist = service.findJwt(jwtToken);
        if (jwtBlacklist == null) {
            service.addJwtToBlackList(jwtToken);
        }
        return new ApiResponse<>(true, "Logout successfully", null);
    }


    @PostMapping("/register")
    public ApiResponse<UserLoginResponse> register(@RequestBody AccountEntity account){
        service.register(account.getUsername(),account.getPassword());
        return new ApiResponse<>(true, "Successfully creating new account", null);
    }

    @PutMapping("/{id}/mute")
    public ApiResponse<String> muteAccount(@PathVariable Long id){
        accountService.suspendAccount(id);
        return new ApiResponse<>(true, "Muting account successfully", null);
    }

    @PutMapping("/{id}/unmute")
    public ApiResponse<String> unmuteAccount(@PathVariable Long id){
        accountService.upliftAccount(id);
        return new ApiResponse<>(true, "Un-muting account successfully", null);
    }


}
