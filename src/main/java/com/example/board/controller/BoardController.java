package com.example.board.controller;

import com.example.board.entity.Member;
import com.example.board.service.MemberService;
import com.example.board.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;

    @GetMapping(value = "/")
    public String boardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member loginMember, Model model) {
        model.addAttribute("show", isLoggedin(loginMember, model));
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
