package com.jyp.safesign.member.service;

import com.jyp.safesign.member.domain.Member;
import com.jyp.safesign.member.dto.MemberResponseDTO;
import com.jyp.safesign.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDTO addMember(String email, String password, String name, String face, String sign, LocalDate createdAt){
        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .face(face)
                .sign(sign)
                .createdAt(createdAt)
                .build();
        Member saved = memberRepository.save(member);
        return MemberResponseDTO.from(saved);
    }

    @Transactional
    public void update(String email, String emailKey, String password) {
        Member member = memberRepository.findMemberByEmail(email);
        member.updateMailKeyAndPassword(emailKey, password);
        memberRepository.save(member);
    }

    @Transactional
    public void updateEmailAuth(String email, String emailKey) {
        Optional<Member> optMember = memberRepository.findMemberByEmailAndEmailKey(email, emailKey);
        Member member;
        if (optMember.isEmpty()) {
            System.out.println("인증되지 않은 사용자입니다.");
        }
        else {
            member = optMember.get();
            member.updateMailAuth();
            System.out.println("인증되었습니다.");
        }
    }

    @Transactional
    public MemberResponseDTO checkMember(String email, String password) {
        Member member = memberRepository.findMemberByEmailAndPassword(email, password);
        return MemberResponseDTO.from(member);
    }

    @Transactional
    public String getMailKey(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        return member.getMailKey();
    }
}
