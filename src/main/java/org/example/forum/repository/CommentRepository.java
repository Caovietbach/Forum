package org.example.forum.repository;

import org.example.forum.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long>, JpaRepository<CommentEntity,Long> {


    List<CommentEntity> findByPostId(Long postId);

}
