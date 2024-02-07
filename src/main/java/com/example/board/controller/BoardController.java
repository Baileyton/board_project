package com.example.board.controller;

import com.example.board.entity.Member;
import com.example.board.entity.Post;
import com.example.board.service.MemberService;
import com.example.board.service.PostService;
import com.example.board.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;

    private final PostService postService;

    @GetMapping(value = "/")
    public String boardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member loginMember,
                            @RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("show", isLoggedin(loginMember, model));

        Page<Post> postsPage = postService.findAll(page, 10); // 한 페이지에 10개의 게시글

        model.addAttribute("posts", postsPage.getContent()); // 현재 페이지의 게시글 목록
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", postsPage.getTotalPages()); // 총 페이지 수

        return "board";
    }

    private boolean isLoggedin(Member loginMember, Model model) {
        if(loginMember == null) {
            return false;
        }
        else {
            model.addAttribute("member", memberService.findById(loginMember.getId()).get());
            return true;
        }
    }

}
