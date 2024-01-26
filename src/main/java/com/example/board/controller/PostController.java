package com.example.board.controller;

import com.example.board.dto.PostHtmlDto;
import com.example.board.dto.PostFormDto;
import com.example.board.entity.Comment;
import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.service.CommentService;
import com.example.board.service.MemberService;
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

    private final MemberService memberService;

    @GetMapping(value = "/write")
    public String writePage(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/writeForm";
    }

    @PostMapping(value = "/write")
    public String write(@Valid PostFormDto postFormDto, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }

        Post post = new Post(loginMember.getNick(), postFormDto.getTitle(), postFormDto.getContent());
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

    @GetMapping(value = "/post/{postId}/edit")
    public String editForm(@PathVariable long postId, Model model) {
        Post post = postService.findById(postId);

        model.addAttribute("post", post);
        model.addAttribute("postFormDto", postService.getEditForm(post));
        return "post/editForm";
    }

    @PostMapping(value = "/post/{postId}/edit")
    public String edit(@PathVariable long postId, @Valid PostFormDto postFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("postFormDto", postFormDto);
            return "post/editForm";
        }

        postService.update(postId, postFormDto);
        return "redirect:/post/" + postId;
    }

    @GetMapping(value = "/post/{postId}/delete")
    public String delete(@PathVariable long postId){
        commentService.deleteComment(postId); // 해당 게시글에 연결된 댓글들을 먼저 삭제
        postService.deletePost(postId);// 후 게시글 삭제

        return "redirect:/";
    }
}
