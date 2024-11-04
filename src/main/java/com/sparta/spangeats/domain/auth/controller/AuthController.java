package com.sparta.spangeats.domain.auth.controller;

import com.sparta.spangeats.domain.auth.dto.request.LoginRequestDto;
import com.sparta.spangeats.domain.auth.dto.request.SignupRequestDto;
import com.sparta.spangeats.domain.auth.dto.response.AuthResponseDto;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody LoginRequestDto requestDto) {
        AuthResponseDto bearerJwt = authService.login(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bearerJwt);
    }

}
