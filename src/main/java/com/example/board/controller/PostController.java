package com.example.board.controller;

import com.example.board.dto.PostHtmlDto;
import com.example.board.dto.WriteFormDto;
import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.service.PostService;
import com.example.board.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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

        PostHtmlDto postHtmlDto = postService.getHtmlPostDto(post);
        model.addAttribute("post", postHtmlDto);

        return "post/postDetail";
    }
}
