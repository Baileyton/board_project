package com.example.board.repository;

import com.example.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findByPostId(Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.postId = :postId")
    void deleteComment(Long postId);
}
