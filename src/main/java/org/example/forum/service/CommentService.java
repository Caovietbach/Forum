package org.example.forum.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.forum.dto.CommentDTO;
import org.example.forum.entity.CommentEntity;
import org.example.forum.response.pagination.CommentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentService {

    CommentListResponse showCommentsOfAPost(Long postId, Integer page, Integer size);

    CommentEntity findCommentById(Long id);

    void writeComment(Long postId, String content);


    void editComment(Long id, String content);

    void deleteComment(Long id);

    Page<CommentDTO> getPage(List<CommentDTO> comments, Pageable pageable);

    CommentListResponse getContent(Page<CommentDTO> comments);


}
