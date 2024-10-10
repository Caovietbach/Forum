package org.example.forum.repository.post;

import org.example.forum.entity.post.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
