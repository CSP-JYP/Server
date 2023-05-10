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

import java.time.LocalDate;

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
    public ResponseEntity<BasicResponse> addMember(@RequestBody MemberDTO requestDTO) {
        String name = requestDTO.getName();
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();
        String face = requestDTO.getFace();
        String sign = requestDTO.getSign();
        LocalDate createdAt = requestDTO.getCreatedAt();

        // DB에 기본정보 insert
        memberService.addMember(email, password, name, face, sign, createdAt);

        //임의의 emailKey 생성 & 이메일 발송
        String mailKey = mailSendService.sendAuthMail(requestDTO.getEmail());

        //DB에 authKey 업데이트
        memberService.updateEmailKey(email, mailKey);

        return basicResponse.noContent();
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