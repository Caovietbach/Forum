package org.example.forum.controller;


import org.example.forum.service.AuthenticationService;
import org.example.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;





}
