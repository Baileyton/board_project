package com.example.board.service;

import com.example.board.dto.EditFormDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public boolean isDuplicate(String nick) {
        Optional<Member> findByNick = Optional.ofNullable(memberRepository.findByNick(nick));
        if(findByNick.isPresent()){
            return true;
        }
        return false;
    }

    public boolean isPasswordCheck(String password, String passwordCheck) {
        if(password.equals(passwordCheck)) {
            return true;
        }
        return false;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member login(String nick, String password) {
        Member member = memberRepository.findByNick((nick));
        if(member == null){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호를 다시 입력해주세요");
        }

        return member;
    }

    public EditFormDto editForm(Member loginMember) {
        return new EditFormDto(loginMember.getNick());
    }

    public void update(Long memberId, EditFormDto editFormDto){
        memberRepository.update(memberId, editFormDto.getNick());
    }
}
