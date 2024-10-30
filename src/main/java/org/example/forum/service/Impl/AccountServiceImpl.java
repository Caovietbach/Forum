package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.dto.AccountInfoDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.AccountInfoEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.repository.AccountInfoRepository;
import org.example.forum.repository.AccountRepository;
import org.example.forum.request.AccountInfoRequest;
import org.example.forum.service.AccountService;
import org.example.forum.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.example.forum.constants.AppConstants.FEMALE;
import static org.example.forum.constants.AppConstants.MALE;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    ModelMapper mapper = new ModelMapper();

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountInfoEntity findInfoByAccountId(long id){
        return accountInfoRepository.findByaccountId(id);
    }

    public AccountEntity findAccountById(long id){
        return accountRepository.findByid(id);
    }



    public AccountInfoDTO showAccountInfo(long id){
        AccountInfoEntity accountInfo = findInfoByAccountId(id);
        String username = accountRepository.findByid(id).getUsername();
        AccountInfoDTO a = mapper.map(accountInfo,AccountInfoDTO.class);
        a.setUsername(username);
        if (accountInfo.getGender() == MALE){
            a.setGender("Male");
        } else if (accountInfo.getGender() == FEMALE){
            a.setGender("Female");
        } else {
            a.setGender("Unknown");
        }
        return a;
    }

    public void editAccountInfo(AccountInfoRequest a, Long id){
        AccountInfoEntity accountInfo = findInfoByAccountId(id);
        accountInfo.setDateOfBirth(a.getDateOfBirth());
        accountInfo.setGender(a.getGender());
        accountInfo.setGender(a.getGender());
        accountInfo.setNationality(a.getNationality());
        accountInfo.setPhoneNumbers(a.getPhoneNumbers());
        accountInfo.setEmail(a.getEmail());
        accountInfo.setDescription(a.getDescription());
        accountInfoRepository.save(accountInfo);
    }


    public void deleteAccount(Long id){
        AccountEntity a = findAccountById(id);
        AccountInfoEntity aie = findInfoByAccountId(id);
        accountRepository.delete(a);
        accountInfoRepository.delete(aie);
    }

    public void suspendAccount(Long id){
        AccountEntity a = findAccountById(id);
        a.setStatus(2);
    }

    public void upliftAccount(Long id){
        AccountEntity a = findAccountById(id);
        a.setStatus(1);
    }


}
