package com.example.board.service;

import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public boolean isAccessable(Long postId, Member loginMember){
        Post post = postRepository.findById(postId).get();
        if(post.getWriter().equals(loginMember.getNick())){
            return true;
        }
        return false;
    }
}
