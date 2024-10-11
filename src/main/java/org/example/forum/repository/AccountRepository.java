package org.example.forum.repository;

import org.example.forum.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByusername(String username);
}
