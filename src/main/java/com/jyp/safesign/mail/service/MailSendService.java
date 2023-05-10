package com.jyp.safesign.mail.service;

import jakarta.mail.Message;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MailSendService {

    @Autowired
    private JavaMailSender mailSender;

    //인증코드 난수 발생
    private String getAuthCode(int size) {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[size];
        rnd.nextBytes(temp);

        return Byte_to_String(temp);
    }

    // 바이트 값을 16진수로 변경해준다
    public String Byte_to_String(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for(byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    //인증메일 보내기
    public String sendAuthMail(String email) {
        //6자리 난수 인증번호 생성
        String mailKey = getAuthCode(16);

        MimeMessage mail = mailSender.createMimeMessage();
        String mailContent = "<h1>[이메일 인증]</h1><br><p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
                + "<a href='http://localhost:8080/signUpConfirm?email="
                + email + "&mailKey=" + mailKey + "' target='_blenk'>이메일 인증 확인</a>";
        try {
            mail.setSubject("회원가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }

        return mailKey;
    }
}