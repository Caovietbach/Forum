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

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;



    @GetMapping("/")
    public ApiResponse<PostListResponse> showPost(@RequestParam(value = "sort", required = false) Integer sort,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestBody(required = false) PostRequest p){
        return new ApiResponse<>(true, "Posts retrieved successfully", postService.showPost(p,sort,page,size));
    }

    @PostMapping("/write")
    public ApiResponse<String> writePost(@RequestBody PostRequest post) {
        postService.writePost(post.getTitle());
        return new ApiResponse<>(true, "Post created successfully", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> editPost(@PathVariable Long id, @RequestBody PostRequest post) {
        authenticationService.checkUser(id, null, null);
        postService.editPost(id, post.getTitle());
        return new ApiResponse<>(true, "Post edited successfully", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePost(@PathVariable Long id) {
        authenticationService.checkUser(id, null, null);
        postService.deletePost(id);
        return new ApiResponse<>(true, "Post deleted successfully", null);
    }

    @PostMapping("{id}/interact")
    public ApiResponse<String> interactPost(@PathVariable Long id, @RequestParam(value = "type") int type){
        postService.interact(id,type);
        return new ApiResponse<>(true, "Post interacted successfully", null);
    }
}