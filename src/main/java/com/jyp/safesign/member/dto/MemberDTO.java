package com.jyp.safesign.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String face;
    private String sign;
    private String mailKey;
    private int mailAuth;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            locale = "Asia/Seoul"
    )
    private LocalDate createdAt;

}
