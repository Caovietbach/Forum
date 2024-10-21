package org.example.forum.service;

import org.example.forum.dto.PostDTO;
import org.example.forum.entity.PostEntity;

import java.util.List;

public interface PostService {

    List<PostDTO> showPost();

    void writePost(Long accountId, String tittle);

    void editPost(Long accountId, String tittle);

    PostEntity getPostById(Long id);

    int getLikeCount(Long postId);

    int getDislikeCount(Long postId);

    void likePost(Long postId, Long accountId);



    void dislikePost(Long postId, Long accountId);

    void deletePost(Long Id);





}
