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

    List<PostDTO> showPost(PostRequest postRequest,Integer sort);
    List<PostDTO> sortByCreatedDate(List<PostDTO> allPosts);

    List<PostDTO> sortByFirstAlphabetInTitle(List<PostDTO> allPosts);

    List<PostDTO> sortByFavouritism(List<PostDTO> allPosts);

    List<PostDTO> sortByTotalInteraction(List<PostDTO> allPosts);

    void writePost(Long accountId, String tittle);

    void editPost(Long accountId, String tittle);

    PostEntity findPostById(Long id);

    int getLikeCount(Long postId);

    int getDislikeCount(Long postId);

    void likePost(Long postId, Long accountId);

    void dislikePost(Long postId, Long accountId);

    void interact(AccountEntity currentAccount, Long postId, int type);

    void deletePost(Long Id);

    Page<PostDTO> getPage(List<PostDTO> posts, Pageable pageable);
    PostListResponse getContent(Page<PostDTO> posts);

}
