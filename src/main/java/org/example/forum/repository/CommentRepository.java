package org.example.forum.repository;

import org.example.forum.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    List<CommentEntity> findByPostId(Long postId);
}
