package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.controller.AuthenticationController;
import org.example.forum.dto.PostDTO;
import org.example.forum.dto.PostInteractionDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.entity.PostInteractionEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.PostInteractionRepository;
import org.example.forum.repository.PostRepository;
import org.example.forum.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostInteractionRepository postInteractionRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public PostEntity getPostById(Long id){
        PostEntity result = postRepository.findById(id).get();
        return result;
    }

    public void save(PostEntity post){
        postRepository.save(post);
    }

    public void writePost(Long accountId, String tittle){

        Date d = new Date(System.currentTimeMillis());

        PostEntity post = new PostEntity();
        post.setAccountId(accountId);
        post.setTitle(tittle);
        post.setCreatedAt(d);
        post.setStatus(1);

        save(post);
    }

    public List<PostDTO> showPost(){
        List<PostDTO> postDTOs = new ArrayList<>();
        List<PostEntity> posts = postRepository.findAll();

        for (PostEntity post : posts) {
            AccountEntity account = accountRepository.findById(post.getAccountId()).orElse(null);
            String username = (account != null) ? account.getUsername() : "Unknown";
            PostDTO p = mapper.map(post,PostDTO.class);
            p.setUsername(username);
            postDTOs.add(p);
        }

        return postDTOs;
    }

    public void editPost(Long id, String title){

        PostEntity post = postRepository.findById(id).get();

        Date d = new Date(System.currentTimeMillis());

        post.setTitle(title);
        post.setCreatedAt(d);
        post.setStatus(1);

        save(post);
    }

    public int getLikeCount(Long postId) {
        return postInteractionRepository.countLikes(postId);
    }

    public int getDislikeCount(Long postId) {
        return postInteractionRepository.countDislikes(postId);
    }

    public void likePost(Long postId, Long accountId) {
        PostInteractionEntity existingInteraction = postInteractionRepository.findByPostIdAndInteractedAccountId(postId, accountId);

        if (existingInteraction == null) {
            PostInteractionEntity newInteraction = new PostInteractionEntity();
            newInteraction.setPostId(postId);
            newInteraction.setInteractedAccountId(accountId);
            newInteraction.setInteractionType(1);
            postInteractionRepository.save(newInteraction);
        } else {
            logger.error("1");
        }
    }

    public void dislikePost(Long postId, Long accountId) {
        PostInteractionEntity existingInteraction = postInteractionRepository.findByPostIdAndInteractedAccountId(postId, accountId);

        if (existingInteraction == null) {
            PostInteractionEntity newInteraction = new PostInteractionEntity();
            newInteraction.setPostId(postId);
            newInteraction.setInteractedAccountId(accountId);
            newInteraction.setInteractionType(2);
            postInteractionRepository.save(newInteraction);
        } else {
            logger.error("2");
        }
    }


    public void deletePost(Long id) {
        PostEntity post = postRepository.findById(id).get();
        post.setStatus(2);
    }





}
