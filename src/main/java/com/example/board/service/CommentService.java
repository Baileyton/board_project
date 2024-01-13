package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment, String nick, Long postId) {
        comment.setNick(nick);
        comment.setPostId(postId);
        comment.setCreateDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);
        return comment;
    }

    public Optional<Comment> findById(Long id){
        return commentRepository.findById(id);
    }

    public List<Comment> findByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }
}
