package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.repository.AccountRepository;
import org.example.forum.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repo;


}
