package com.example.board.controller;

import com.example.board.dto.PostHtmlDto;
import com.example.board.dto.WriteFormDto;
import com.example.board.entity.Comment;
import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.service.CommentService;
import com.example.board.service.PostService;
import com.example.board.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    @GetMapping(value = "/write")
    public String writePage(Model model) {
        model.addAttribute("writeFormDto", new WriteFormDto());
        return "post/writeForm";
    }

    @PostMapping(value = "/write")
    public String write(@Valid WriteFormDto writeFormDto, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }

        Post post = new Post(loginMember.getNick(), writeFormDto.getTitle(), writeFormDto.getContent());
        Post savedPost = postService.save(post);

        redirectAttributes.addAttribute("postId",savedPost.getId());
        return "redirect:/post/{postId}";
    }

    @GetMapping(value = "/post/{postId}")
    public String postIdPage(@PathVariable long postId, Model model,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Post post = postService.findById(postId);

        if(postService.isAccessable(postId, loginMember)){
            model.addAttribute("access",true);
        }else {
            postService.addView(postId); // 조회수
        }

        List<Comment> comments = commentService.findByPostId(postId);
        model.addAttribute("comments", comments);
        PostHtmlDto postHtmlDto = postService.getHtmlPostDto(post);
        model.addAttribute("post", postHtmlDto);

        model.addAttribute("commentForm", new Comment());

        return "post/postDetail";
    }

    @PostMapping(value = "/post/{postId}")
    public String writeComment(@PathVariable long postId, @ModelAttribute("commentForm")Comment comment, @Valid BindingResult bindingResult,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        if(bindingResult.hasErrors()) {
            return "redirect:/post/{postId}";
        }
        commentService.save(comment, loginMember.getNick(), postId);
        return "redirect:/post/{postId}";
    }
}
