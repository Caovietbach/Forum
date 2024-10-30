package org.example.forum.repository;

import org.example.forum.entity.PostInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostInteractionRepository extends CrudRepository <PostInteractionEntity, Long>, JpaRepository<PostInteractionEntity,Long> {

    PostInteractionEntity findByPostIdAndInteractedAccountId(Long postId, Long accountId);

    @Query("SELECT COUNT(pIE) FROM PostInteractionEntity pIE WHERE pIE.postId = :postId AND pIE.interactionType = :interactionType")
    int countInteraction(@Param("postId") Long postId, int interactionType);


}
