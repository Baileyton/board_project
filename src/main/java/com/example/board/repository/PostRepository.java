package com.example.board.repository;

import com.example.board.entity.Member;
import com.example.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post post);

    Optional<Post> findById(Long id);
}
