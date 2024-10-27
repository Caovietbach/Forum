package org.example.forum.controller;

import org.example.forum.dto.PostDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.request.PostRequest;
import org.example.forum.response.api.ApiResponse;
import org.example.forum.response.pagination.PostListResponse;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private static final int SORT_BY_FIRST_ALPHABET_IN_TITLE = 1;
    private static final int SORT_BY_FAVOURITISM = 2;
    private static final int SORT_BY_TOTAL_INTERACTIONS = 3;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("/")
    public ApiResponse<PostListResponse> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        List<PostDTO> listPosts = postService.showPost();
        for (PostDTO post : listPosts) {
            post.setLikeCount(postService.getLikeCount(post.getId()));
            post.setDislikeCount(postService.getDislikeCount(post.getId()));
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<PostDTO> posts = postService.getPage(listPosts, pageable);
        return new ApiResponse<>(true, "Posts retrieved successfully", postService.getContent(posts));
    }

    @PostMapping("/search")
    public ApiResponse<PostListResponse> search(@RequestParam(value = "sort", required = false) Integer sort,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestBody PostRequest p){
        Pageable pageable = PageRequest.of(page, size);
        List<PostDTO> listPosts = new ArrayList<>();

        if(p.getUsername() != null || p.getTitle() != null){
            listPosts = postService.search(p);
        }
        listPosts = postService.sortByCreatedDate(listPosts);
        if (sort != null){
            if (sort == SORT_BY_FIRST_ALPHABET_IN_TITLE) {
                listPosts = postService.sortByFirstAlphabetInTitle(listPosts);
            }
            if (sort == SORT_BY_FAVOURITISM) {
                listPosts = postService.sortByFavouritism(listPosts);
            }
            if (sort == SORT_BY_TOTAL_INTERACTIONS) {
                listPosts = postService.sortByTotalInteraction(listPosts);
            }
        }

        Page<PostDTO> posts = postService.getPage(listPosts, pageable);
        return new ApiResponse<>(true, "Posts retrieved successfully", postService.getContent(posts));
    }

    @PostMapping("/write")
    public ApiResponse<String> writePost(@RequestBody PostRequest post) {
        AccountEntity currentAccount = authenticationService.extractUser();
        if (currentAccount == null){
            throw new ValidateException("Please login to write a post");
        }
        if (currentAccount.getStatus() == 2){
            throw new ValidateException("Your account has been muted, you can't write a post");
        }
        logger.info("User name is: {}", currentAccount.getUsername());
        postService.writePost(currentAccount.getId(), post.getTitle());
        return new ApiResponse<>(true, "Post created successfully", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> editPost(@PathVariable Long id, @RequestBody PostRequest post) {
        postService.checkUser(id);
        postService.editPost(id, post.getTitle());
        return new ApiResponse<>(true, "Post edited successfully", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePost(@PathVariable Long id) {
        postService.checkUser(id);
        postService.deletePost(id);
        return new ApiResponse<>(true, "Post deleted successfully", null);
    }

    @PostMapping("/like/{id}")
    public ApiResponse<String> likePost(@PathVariable Long id) {
        AccountEntity currentAccount = authenticationService.extractUser();
        if (currentAccount == null){
            throw new ValidateException("Please login to dislike this post");
        }
        postService.likePost(id, currentAccount.getId());
        return new ApiResponse<>(true, "Post liked successfully", null);
    }

    @PostMapping("/dislike/{id}")
    public ApiResponse<String> dislikePost(@PathVariable Long id) {
        AccountEntity currentAccount = authenticationService.extractUser();
        if (currentAccount == null){
            throw new ValidateException("Please login to dislike this post");
        }
        postService.dislikePost(id, currentAccount.getId());
        return new ApiResponse<>(true, "Post disliked successfully", null);
    }
}