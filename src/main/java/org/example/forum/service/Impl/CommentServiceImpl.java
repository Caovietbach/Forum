package org.example.forum.service.Impl;


import jakarta.transaction.Transactional;
import org.example.forum.dto.CommentDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.entity.CommentEntity;
import org.example.forum.repository.AccountRepository;
import org.example.forum.repository.CommentRepository;
import org.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public CommentEntity getCommentById(Long id){
        CommentEntity result = commentRepository.findById(id).get();
        return result;
    }

    public void save(CommentEntity comment){
        commentRepository.save(comment);
    }

    public void writecomment(Long accountId, Long postId, String content){

        Date d = new Date(System.currentTimeMillis());

        CommentEntity comment = null;
        comment.setAccountId(accountId);
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setCreatedAt(d);
        comment.setStatus(1);

        save(comment);
    }

    public List<CommentDTO> showCommentsOfAPost(Long commentId){
        List<CommentDTO> commentDTOs = new ArrayList<>();
        List<CommentEntity> comments = commentRepository.findByPostId(commentId);

        for (CommentEntity comment : comments) {
            AccountEntity account = accountRepository.findById(comment.getAccountId()).orElse(null);
            String username = (account != null) ? account.getUsername() : "Unknown";
            commentDTOs.add(new CommentDTO(username, commentId, comment.getContent(), comment.getCreatedAt(), comment.getStatus()));
        }

        return commentDTOs;
    }

    public void editComment(Long accountId, Long id, String content){

        CommentEntity comment = commentRepository.findById(id).get();

        Date d = new Date(System.currentTimeMillis());

        comment.setContent(content);
        comment.setCreatedAt(d);
        comment.setStatus(1);

        save(comment);
    }



    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
