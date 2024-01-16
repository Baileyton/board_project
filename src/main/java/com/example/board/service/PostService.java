package com.example.board.service;

import com.example.board.dto.PostFormDto;
import com.example.board.dto.PostHtmlDto;
import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void addView(Long postId){
        postRepository.addView(postId);
    }

    public void deletePost(Long postId){
        postRepository.deletePost(postId);
    }

    public boolean isAccessable(Long postId, Member loginMember){
        Post post = postRepository.findById(postId).get();
        return post.getWriter().equals(loginMember.getNick());
    }

    public PostHtmlDto getHtmlPostDto(Post post) {
        PostHtmlDto postHtmlDto = new PostHtmlDto();
        postHtmlDto.setId(post.getId());
        postHtmlDto.setWriter(post.getWriter());
        postHtmlDto.setTitle(post.getTitle());
        String htmlContent = getHtmlContent(post.getContent());
        postHtmlDto.setContent(htmlContent);
        postHtmlDto.setCreateDate(post.getCreateDate());
        postHtmlDto.setModifiedDate(post.getModifiedDate());
        postHtmlDto.setViews(post.getViews());
        return postHtmlDto;
    }

    public String getHtmlContent(String content) {
        return content.replaceAll("\n", "<br>");
    }

    public PostFormDto getEditForm(Post post) {
        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setTitle(post.getTitle());
        postFormDto.setContent(post.getContent());
        return postFormDto;
    }

    public void update(Long postId, PostFormDto postFormDto) {
        postRepository.update(postId, postFormDto.getTitle(), postFormDto.getContent());
    }
}
