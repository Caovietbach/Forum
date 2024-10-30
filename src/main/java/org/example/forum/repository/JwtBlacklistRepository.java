package org.example.forum.repository;

import jakarta.transaction.Transactional;
import org.example.forum.entity.JwtBlacklist;
import org.example.forum.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    JwtBlacklist findByJwt(String jwt);

    @Modifying
    @Query("DELETE FROM JwtBlacklist j WHERE j.expirationDate <= :deletionDate")
    void deleteAllExpiredJWT(Long deletionDate);

}

