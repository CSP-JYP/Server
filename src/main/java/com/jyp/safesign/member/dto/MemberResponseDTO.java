package com.jyp.safesign.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jyp.safesign.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class MemberResponseDTO {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String face;
    private final String sign;
    private final String mailKey;
    private final int mailAuth;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            locale = "Asia/Seoul"
    )
    private final LocalDate createdAt;
    private final LocalDate modifiedAt;

    public static MemberResponseDTO from (Member member){
        return MemberResponseDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .face(member.getFace())
                .sign(member.getSign())
                .mailKey(member.getMailKey())
                .mailAuth(member.getMailAuth())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();

    }
}
