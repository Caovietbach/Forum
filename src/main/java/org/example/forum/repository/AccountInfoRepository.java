package org.example.forum.repository;

import org.example.forum.entity.AccountInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long> {

}
