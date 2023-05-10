package com.jyp.safesign.member.repository;

import com.jyp.safesign.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmail(String email);

    @Query("select distinct m from Member m " +
            "where m.email = :email and " +
            "m.mailKey = :mailKey")
    Optional<Member> findMemberByEmailAndEmailKey(@Param("email")String email, @Param("mailKey")String mailKey);
}
