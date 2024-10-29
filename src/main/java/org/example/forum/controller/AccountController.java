package org.example.forum.controller;


import org.example.forum.dto.AccountInfoDTO;
import org.example.forum.dto.PostDTO;
import org.example.forum.repository.AccountInfoRepository;
import org.example.forum.request.AccountInfoRequest;
import org.example.forum.response.api.ApiResponse;
import org.example.forum.response.pagination.PostListResponse;
import org.example.forum.service.AccountService;
import org.example.forum.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ApiResponse<AccountInfoDTO> showAccountProfile(@PathVariable Long id) {
        authenticationService.checkUser(null, null, id);
        AccountInfoDTO a = accountService.showAccountInfo(id);
        return new ApiResponse<>(true, "Account profile retrieved successfully", a);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> editProfile(@PathVariable Long id, @RequestBody AccountInfoRequest a){
        authenticationService.checkUser(null, null, id);
        accountService.editAccountInfo(a);
        return new ApiResponse<>(true, "Modify account profile successfully", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAccount(@PathVariable Long id){
        authenticationService.checkUser(null, null, id);
        accountService.deleteAccount(id);
        return new ApiResponse<>(true, "Account deleted successfully", null);
    }


}
