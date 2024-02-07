package com.example.board.service;

import com.example.board.dto.PostFormDto;
import com.example.board.dto.PostHtmlDto;
import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Page<Post> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }

    public List<Post> sortByLatest(List<Post> list){
        Collections.sort(list, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return list;
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
        return content;
    }

    public PostFormDto getEditForm(Post post) {
        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setTitle(post.getTitle());
        postFormDto.setContent(post.getContent());
        return postFormDto;
    }

    public void update(Long postId, PostFormDto postFormDto) {
        Optional<Post> existingPost = postRepository.findById(postId);

        if(existingPost.isPresent()) {
            Post postIdUpdate = existingPost.get();
            postIdUpdate.setTitle(postFormDto.getTitle());
            postIdUpdate.setContent(postFormDto.getContent());
            postRepository.save(postIdUpdate);
        } else {
            throw new RuntimeException("Post not found with id = " + postId);
        }
    }

    public List<Post> searchPosts(String searchType, String keyword) {
        if ("writer".equals(searchType)) {
            return sortByLatest(postRepository.findByWriterContaining(keyword));
        } else if ("content".equals(searchType)) {
            return sortByLatest(postRepository.findByContentContaining(keyword));
        } else {
            return new ArrayList<>();
        }
    }
}
