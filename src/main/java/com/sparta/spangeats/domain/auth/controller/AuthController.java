package com.sparta.spangeats.domain.auth.controller;

import com.sparta.spangeats.domain.auth.dto.request.SignupRequestDto;
import com.sparta.spangeats.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        log.info("회원가입 요청 시작: " + requestDto);
        String bearerJwt = String.valueOf(authService.signup(requestDto));
        log.info("회원가입 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료! \n Bearer JWT : " + bearerJwt);
    }

}
