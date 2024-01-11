package com.example.board.service;

import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

}
