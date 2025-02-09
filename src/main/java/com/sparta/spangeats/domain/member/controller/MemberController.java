package com.sparta.spangeats.domain.member.controller;

import com.sparta.spangeats.domain.member.dto.request.SignoutRequestDto;
import com.sparta.spangeats.domain.member.dto.request.UpdateMemberRequestDto;
import com.sparta.spangeats.domain.member.dto.response.MemberInfoResponseDto;
import com.sparta.spangeats.domain.member.service.MemberService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/signout")
    public ResponseEntity<String> signout(@RequestBody SignoutRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.signout(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 완료! 중복된 이메일로 재가입이 불가합니다.");

    }

    @GetMapping
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MemberInfoResponseDto responseDto = memberService.getMemberInfo(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping
    public ResponseEntity<String> updateMemberInfo(@Valid @RequestBody UpdateMemberRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.updateMemberInfo(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보가 성공적으로 수정되었습니다.");
    }

}
