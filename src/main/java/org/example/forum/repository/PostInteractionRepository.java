package org.example.forum.repository;

import org.example.forum.entity.PostInteractionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInteractionRepository extends CrudRepository <PostInteractionEntity, Long> {

}
