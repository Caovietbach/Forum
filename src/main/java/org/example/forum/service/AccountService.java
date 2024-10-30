package org.example.forum.service;

import org.example.forum.dto.AccountInfoDTO;
import org.example.forum.request.AccountInfoRequest;

public interface AccountService {

    AccountInfoDTO showAccountInfo(long id);

    void editAccountInfo(AccountInfoRequest a, Long id);


    void deleteAccount(Long id);

    void suspendAccount(Long id);

    void upliftAccount(Long id);

}
