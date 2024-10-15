package org.example.forum.service.Impl;

import jakarta.transaction.Transactional;
import org.example.forum.dto.PostDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.PostEntity;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.PostRepository;
import org.example.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    public PostEntity getPostById(Long id){
        PostEntity result = postRepository.findById(id).get();
        return result;
    }

    public void save(PostEntity post){
        postRepository.save(post);
    }

    public void writePost(Long accountId, String tittle){

        Date d = new Date(System.currentTimeMillis());

        PostEntity post = null;
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
            postDTOs.add(new PostDTO(username, post.getTitle(), post.getCreatedAt(),post.getStatus()));
        }

        return postDTOs;
    }

    public void editPost(Long accountId, String tittle){

        Date d = new Date(System.currentTimeMillis());

        PostEntity post = null;
        post.setAccountId(accountId);
        post.setTitle(tittle);
        post.setCreatedAt(d);
        post.setStatus(1);

        save(post);
    }



    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }



}
