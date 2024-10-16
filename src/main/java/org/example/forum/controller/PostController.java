package org.example.forum.controller;

import org.example.forum.dto.PostDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/forum")
    public String mainPage(Model model) {
        List<PostDTO> listPosts = postService.showPost();
        model.addAttribute("listPosts", listPosts);
        return "mainPage";
    }

    @GetMapping("/writePost")
    public String writePostPage() {
        return "writePost";
    }

    @PostMapping("/writePost")
    public String writePost(@CookieValue(name = "jwtToken", required = false) String jwtToken,@RequestParam("tittle") String tittle){
        if (jwtToken == null) {
            logger.warn("No jwtToken found in request");
            return "redirect:/login";
        }
        AccountEntity currentAccount = authenticationService.extractUser(jwtToken);
        postService.writePost(currentAccount.getId(), tittle);
        return "redirect:/forum";
    }

    @GetMapping("/editPost/{id}")
    public String editPostPage(@PathVariable Long id, Model model) {
        PostEntity post = postService.getPostById(id);
        if (post != null) {
            model.addAttribute("post", post);
            return "editPost";
        } else {
            return "redirect:/forum";
        }
    }

    @PostMapping("/editPost/{id}")
    public String editPost(@PathVariable Long id, @RequestParam("title") String title) {
        postService.editPost(id, title);
        return "redirect:/forum";
    }


    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/forum";
    }


}
