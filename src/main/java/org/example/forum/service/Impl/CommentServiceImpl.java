package org.example.forum.service.Impl;


import jakarta.transaction.Transactional;
import org.example.forum.dto.CommentDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.CommentEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.CommentRepository;
import org.example.forum.response.pagination.CommentListResponse;
import org.example.forum.service.AuthenticationService;
import org.example.forum.service.CommentService;
import org.modelmapper.ModelMapper;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    ModelMapper mapper = new ModelMapper();

    public CommentEntity findCommentById(long id){
        return commentRepository.findByid(id);
    }

    public List<CommentEntity> getCommentByPostId(Long id){
        List<CommentEntity> result = commentRepository.findByPostId(id);
        return result;
    }

    public void save(CommentEntity comment){
        commentRepository.save(comment);
    }

    public void writeComment(Long accountId, Long postId, String content){

        AccountEntity currentAccount = accountRepository.findByid(accountId);
        if(currentAccount.getStatus() == 2){
            throw new ValidateException("Your account has been muted. You cannot write a post until an admin lifted the mute. Please connect to the admin to discuss an uplift");
        }

        Date d = new Date(System.currentTimeMillis());

        CommentEntity comment = null;
        comment.setAccountId(accountId);
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setCreatedAt(d);
        comment.setStatus(1);

        save(comment);
    }

    public List<CommentDTO> showCommentsOfAPost(Long postId){
        List<CommentDTO> commentDTOs = new ArrayList<>();
        List<CommentEntity> comments = commentRepository.getActiveComment(postId);

        for (CommentEntity comment : comments) {
            AccountEntity account = accountRepository.findById(comment.getAccountId()).orElse(null);
            String username = (account != null) ? account.getUsername() : "Unknown";
            CommentDTO c = mapper.map(comment,CommentDTO.class);
            c.setUsername(username);
            commentDTOs.add(c);

        }
        Collections.reverse(commentDTOs);

        return commentDTOs;
    }

    public void editComment(Long id, String content){

        CommentEntity comment = findCommentById(id);

        Date d = new Date(System.currentTimeMillis());

        comment.setContent(content);
        comment.setCreatedAt(d);
        comment.setStatus(1);

        save(comment);
    }



    public void deleteComment(Long id) {
        CommentEntity comment = findCommentById(id);
        comment.setStatus(2);
    }
    public Page<CommentDTO> getPage(List<CommentDTO> comments, Pageable pageable) {
        int total = comments.size();
        List<CommentDTO> paginatedList = comments.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paginatedList, pageable, total);
    }
    public CommentListResponse getContent(Page<CommentDTO> comments){
        CommentListResponse data = new CommentListResponse(comments.getTotalElements(),comments.getTotalPages(), comments.getSize(), comments.getContent());
        return data;
    }

    public void checkUser(Long id){
        AccountEntity currentAccount = authenticationService.extractUser();
        CommentEntity comment = findCommentById(id);
        if(!currentAccount.getId().equals(comment.getAccountId())){
            throw new ValidateException("This is the comment from another account, you can't do this function");
        }
    }

    public void reverseDeleteForComment(Long id){
        CommentEntity comment = findCommentById(id);
        comment.setStatus(1);
    }


}
