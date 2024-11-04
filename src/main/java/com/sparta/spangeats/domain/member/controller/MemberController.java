package com.sparta.spangeats.domain.member.controller;

import com.sparta.spangeats.domain.member.dto.SignoutRequestDto;
import com.sparta.spangeats.domain.member.service.MemberService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //회원 탈퇴 기능
    @DeleteMapping("/signout")
    public ResponseEntity<String> signout(@RequestBody SignoutRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        memberService.signout(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 완료! 중복된 이메일로 재가입이 불가합니다.");

    }

}
