package com.example.board.repository;

import com.example.board.entity.Member;
import com.example.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :postId")
    void addView(Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    void deletePost(Long postId);
}
