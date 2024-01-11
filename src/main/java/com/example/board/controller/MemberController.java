package com.example.board.controller;

import com.example.board.dto.JoinFormDto;
import com.example.board.dto.LoginFormDto;
import com.example.board.entity.Member;
import com.example.board.service.MemberService;
import com.example.board.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberService memberService;

    //회원가입 화면
    @GetMapping(value = "/join")
    public String joinForm(JoinFormDto joinFormDto, Model model){
        model.addAttribute("memberDto", new JoinFormDto());
        return "member/joinForm";
    }

    //회원가입
    @PostMapping(value = "/join")
    public String join(@Valid JoinFormDto joinFormDto, BindingResult bindingResult, Model model) {

        if(memberService.isDuplicate(joinFormDto.getNick())){
            bindingResult.rejectValue("nick", "duplicateNick", "이미 있는 닉네임 입니다.");
        }

        if(!memberService.isPasswordCheck(joinFormDto.getPassword(), joinFormDto.getPasswordCheck())){
            bindingResult.rejectValue("passwordCheck", "notCoincidePassword","두 비밀번호가 일치하지 않습니다.");
        }

        if(bindingResult.hasErrors()) {
            //유효성 검사
            return "member/joinForm";
        }

        try{
            String encodedPassword = passwordEncoder.encode(joinFormDto.getPassword());
            Member member = Member.join(joinFormDto, encodedPassword);
            memberService.save(member);
            log.info("join info: " + member);
        } catch (IllegalAccessError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/joinForm";
        }

        return "board";
    }

    //로그인 화면
    @GetMapping(value = "/login")
    public String loginForm(LoginFormDto loginFormDto, Model model){
        model.addAttribute("memberDto", new JoinFormDto());
        return "member/loginForm";
    }

    //로그인
    @PostMapping(value = "/login")
    public String login(@Valid LoginFormDto loginFormDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "member/loginForm";
        }
        try{
            String nick = loginFormDto.getNick();
            String password = loginFormDto.getPassword();

            //사용자 검증
            Member member = memberService.login(nick, password);

            if(member == null) {
                bindingResult.reject("loginFail");
                return "member/loginForm";
            }

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
            log.info("login info: " + member);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/loginForm";
        }
    }

}
