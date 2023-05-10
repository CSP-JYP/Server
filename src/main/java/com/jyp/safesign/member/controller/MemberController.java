package com.jyp.safesign.member.controller;

import com.jyp.safesign.common.dto.BasicResponse;
import com.jyp.safesign.mail.service.MailSendService;
import com.jyp.safesign.member.dto.MemberDTO;
import com.jyp.safesign.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "member", description = "member API")
public class MemberController {

    private final MailSendService mailSendService;
    private final BasicResponse basicResponse = new BasicResponse();
    private final MemberService memberService;

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    public ResponseEntity<BasicResponse> addMember(@RequestBody MemberDTO requestDTO) throws Exception {
        String name = requestDTO.getName();
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();
        String face = requestDTO.getFace();
        String sign = requestDTO.getSign();
        LocalDate createdAt = requestDTO.getCreatedAt();

        // DB에 기본정보 insert
        memberService.addMember(email, password, name, face, sign, createdAt);

        //임의의 mailKey 생성 & 이메일 발송
        String mailKey = mailSendService.sendAuthMail(requestDTO.getEmail());

        //DB에 mailKey 및 비밀번호 h(pw||mailKey) 업데이트
        memberService.update(email, mailKey, Hashing(password.getBytes(), mailKey));

        return basicResponse.noContent();
    }

    // 비밀번호 해싱
    private String Hashing(byte[] password, String mailKey) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");    // SHA-256 해시함수를 사용

        // key-stretching
        for(int i = 0; i < 10000; i++) {
            String temp = password + mailKey;                // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.getBytes());                        // temp 의 문자열을 해싱하여 md 에 저장해둔다
            password = md.digest();        // md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return mailSendService.Byte_to_String(password);
    }

    @GetMapping("/signUpConfirm")
    @Operation(summary = "회원가입 이메일 확인", description = "회원가입 시 입력한 이메일의 유효성을 확인합니다.")
    public ResponseEntity<BasicResponse> emailConfirm(@RequestParam String email, @RequestParam String mailKey) {
        memberService.updateEmailAuth(email, mailKey);
        return basicResponse.ok(
                "redirect:/"
        );
    }
}