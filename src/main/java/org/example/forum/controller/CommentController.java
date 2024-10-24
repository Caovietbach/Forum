package org.example.forum.controller;


import org.example.forum.dto.CommentDTO;
import org.example.forum.dto.PostDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.CommentEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.response.api.ApiResponse;
import org.example.forum.response.pagination.CommentListResponse;
import org.example.forum.response.pagination.PostListResponse;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.CommentService;
import org.example.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @GetMapping("/{id}")
    public ApiResponse<CommentListResponse> getAllComments(@PathVariable Long id ,@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        List<CommentDTO> listComments = commentService.showCommentsOfAPost(id);
        Pageable pageable = PageRequest.of(page,size);
        Page<CommentDTO> comments = commentService.getPage(listComments, pageable);
        return new ApiResponse<>(true, "Comments retrieved successfully", commentService.getContent(comments));
    }
    @PostMapping("/{id}/writeComment")
    public ApiResponse<String> writePost(@PathVariable Long id, @RequestParam("content") String content) {
        AccountEntity currentAccount = authenticationService.extractUser();
        if (currentAccount == null){
            throw new ValidateException("Please login to write a comment");
        }
        logger.info("User name is: {}", currentAccount.getUsername());
        commentService.writeComment(currentAccount.getId(),id, content);
        return new ApiResponse<>(true, "Post created successfully", null);
    }

    @PostMapping("/editPost/{id}")
    public ApiResponse<String> editPost(@PathVariable Long id, @RequestParam("content") String content) {
        AccountEntity currentAccount = authenticationService.extractUser();
        CommentEntity comment = commentService.findCommentById(id);
        if(!currentAccount.getId().equals(comment.getAccountId())){
            throw new ValidateException("This comment is not written by you");
        }
        commentService.editComment(id, content);
        return new ApiResponse<>(true, "Post edited successfully", null);
    }

    @DeleteMapping("/deletePost/{id}")
    public ApiResponse<String> deletePost(@PathVariable Long id) {
        AccountEntity currentAccount = authenticationService.extractUser();
        CommentEntity comment = commentService.findCommentById(id);
        if(!currentAccount.getId().equals(comment.getAccountId())){
            throw new ValidateException("This comment is not written by you");
        }
        commentService.deleteComment(id);
        return new ApiResponse<>(true, "Post deleted successfully", null);
    }



}
