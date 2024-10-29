package org.example.forum.repository;

import org.example.forum.entity.CommentEntity;
import org.example.forum.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long>,JpaRepository<PostEntity,Long> {
    @Query("SELECT p FROM PostEntity p WHERE " +
            "(:accountId IS NULL OR p.accountId = :accountId) AND " +
            "(:title IS NULL OR p.title LIKE %:title%) AND" +
            "(p.status != 2)")
    List<PostEntity> searchBy(
            @Param("accountId") Long accountId,
            @Param("title") String title);


    @Query("SELECT p FROM PostEntity p WHERE " +
            "(p.status != 2)")
    List<PostEntity> getActivePost();
}
