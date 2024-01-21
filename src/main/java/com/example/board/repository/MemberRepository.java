package com.example.board.repository;

import com.example.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNick(String nick);

    @Modifying
    @Transactional
    @Query("UPDATE Member m set m.nick = :nick WHERE m.id = :memberId")
    void update(@Param("memberId") Long memberId, @Param("nick") String nick);

}
