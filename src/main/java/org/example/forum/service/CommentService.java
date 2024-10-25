package org.example.forum.service;

import org.example.forum.dto.CommentDTO;
import org.example.forum.entity.CommentEntity;
import org.example.forum.response.pagination.CommentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentService {

    List<CommentEntity> getCommentByPostId(Long id);

    CommentEntity findCommentById(long id);

    void writeComment(Long accountId, Long postId, String content);

    List<CommentDTO> showCommentsOfAPost(Long commentId);

    void editComment(Long id, String content);

    void deleteComment(Long id);

    Page<CommentDTO> getPage(List<CommentDTO> comments, Pageable pageable);

    CommentListResponse getContent(Page<CommentDTO> comments);

    void checkUser(Long id);

}
