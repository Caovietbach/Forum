package org.example.forum.repository;

import org.example.forum.entity.CommentEntity;
import org.example.forum.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long>,JpaRepository<PostEntity,Long> {


}
