package org.example.forum.repository;

import org.example.forum.entity.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    JwtBlacklist findByJwt(String jwt);
}

