package org.example.forum.repository;

import org.example.forum.entity.CommentEntity;
import org.example.forum.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long>, JpaRepository<CommentEntity,Long> {


    List<CommentEntity> findByPostId(Long postId);

    CommentEntity findByid(Long commentId);

    @Query("SELECT c FROM CommentEntity c WHERE " +
            "(:status != 2) AND" +
            "(c.postId = :postId) ")
    List<CommentEntity> getActiveComment(Long postId);


}
