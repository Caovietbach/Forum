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
import org.example.forum.request.PostRequest;
import org.example.forum.response.pagination.PostListResponse;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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
        Collections.reverse(postDTOs);
        return postDTOs;
    }

    public List<PostDTO> search(PostRequest postRequest){
        List<PostDTO> postDTOs = new ArrayList<>();
        Long accountId = accountRepository.findByusername(postRequest.getUsername()).getId();
        List<PostEntity> posts = postRepository.searchBy(accountId,postRequest.getTitle());

        for (PostEntity post : posts) {
            AccountEntity account = accountRepository.findById(post.getAccountId()).orElse(null);
            String username = (account != null) ? account.getUsername() : "Unknown";
            PostDTO p = mapper.map(post,PostDTO.class);
            p.setUsername(username);
            postDTOs.add(p);
        }
        postDTOs = sortByCreatedDate(postDTOs);
        return postDTOs;
    }

    public List<PostDTO> sortByCreatedDate(List<PostDTO> allPosts) {
        return allPosts.stream()
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<PostDTO> sortByFirstAlphabetInTitle(List<PostDTO> allPosts) {
        return allPosts.stream()
                .sorted((s1, s2) -> s1.getTitle().compareTo(s2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<PostDTO> sortByTotalInteraction(List<PostDTO> allPosts) {
        return allPosts.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getLikeCount()+s2.getDislikeCount(), s1.getLikeCount()+s1.getDislikeCount()))
                .collect(Collectors.toList());
    }

    public List<PostDTO> sortByFavouritism(List<PostDTO> allPosts){
        return allPosts.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getLikeCount()-s2.getDislikeCount(), s1.getLikeCount()-s1.getDislikeCount()))
                .collect(Collectors.toList());
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
        } else if (existingInteraction.getInteractionType() == 2){
            existingInteraction.setInteractionType(1);
            postInteractionRepository.save(existingInteraction);
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
        } else if (existingInteraction.getInteractionType() == 1){
            existingInteraction.setInteractionType(2);
            postInteractionRepository.save(existingInteraction);
        } else {
            logger.error("2");
        }
    }


    public void deletePost(Long id) {
        PostEntity post = postRepository.findById(id).get();
        post.setStatus(2);
    }

    public Page<PostDTO> getPage(List<PostDTO> posts, Pageable pageable) {
        int total = posts.size();
        List<PostDTO> paginatedList = posts.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paginatedList, pageable, total);
    }
    public PostListResponse getContent(Page<PostDTO> posts){
        PostListResponse data = new PostListResponse(posts.getTotalElements(),posts.getTotalPages(), posts.getSize(), posts.getContent());
        return data;
    }

    public void checkUser(long id){
        AccountEntity currentAccount = authenticationService.extractUser();
        PostEntity post = getPostById(id);
        if(!currentAccount.getId().equals(post.getAccountId())){
            throw new ValidateException("This post is not written by you");
        }
    }







}
