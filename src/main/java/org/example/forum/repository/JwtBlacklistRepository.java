package org.example.forum.repository;

import org.example.forum.entity.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    JwtBlacklist findByJwt(String jwt);
}

