package org.example.forum.repository.account;

import org.example.forum.entity.account.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
}
