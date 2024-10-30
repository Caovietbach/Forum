package org.example.forum.service;

import org.example.forum.dto.PostDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.request.PostRequest;
import org.example.forum.response.pagination.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostListResponse showPost(PostRequest p, Integer sort,Integer page, Integer size);

    void writePost(String tittle);

    void editPost(Long id, String title);

    PostEntity findPostById(Long id);


    void interact(Long postId, int type);

    void deletePost(Long Id);

    Page<PostDTO> getPage(List<PostDTO> posts, Pageable pageable);
    PostListResponse getContent(Page<PostDTO> posts);

}
