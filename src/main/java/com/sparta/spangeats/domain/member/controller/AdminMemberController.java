package com.sparta.spangeats.domain.member.controller;

import com.sparta.spangeats.domain.member.dto.response.AdminMemberInfoResponse;
import com.sparta.spangeats.domain.member.service.MemberService;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<List<AdminMemberInfoResponse>> getAllMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                          @RequestParam int page,
                                                                          @RequestParam int size) {
        List<AdminMemberInfoResponse> memberInfoResponseList = memberService.getAllMemberInfo(userDetails, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(memberInfoResponseList);
    }


}
