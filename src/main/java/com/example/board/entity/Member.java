package com.example.board.entity;

import com.example.board.dto.EditFormDto;
import com.example.board.dto.JoinFormDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nick", unique = true)
    private String nick;

    private String password;

    public static Member join(JoinFormDto memberDto, String encodedPassword){
        Member member = new Member();
        member.setNick(memberDto.getNick());
        member.setPassword(encodedPassword);
        return member;
    }

    public static Member edit(EditFormDto editFormDto){
        Member member = new Member();
        member.setNick(editFormDto.getNick());
        return member;
    }
}
