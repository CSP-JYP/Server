package com.jyp.safesign.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String face;

    @Column(nullable = false)
    private String sign;

    @Column(length = 6)
    private String mailKey;

    @ColumnDefault("0")
    private int mailAuth;

    @Column(nullable = false)
    private LocalDate createdAt;

    private LocalDate modifiedAt;

    @Builder
    public Member(Long id, String email, String password, String name, String face, String sign, String mailKey, int mailAuth, LocalDate createdAt, LocalDate modifiedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.face = face;
        this.sign = sign;
        this.mailKey = mailKey;
        this.mailAuth = mailAuth;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateMailKey(String mailKey) {
        this.mailKey = mailKey;
    }

    public void updateMailAuth() {
        this.mailAuth = 1;
    }
}
