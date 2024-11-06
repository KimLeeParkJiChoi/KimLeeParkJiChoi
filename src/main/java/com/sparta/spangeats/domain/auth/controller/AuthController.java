package com.sparta.spangeats.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.spangeats.domain.auth.dto.request.LoginRequestDto;
import com.sparta.spangeats.domain.auth.dto.request.SignupRequestDto;
import com.sparta.spangeats.domain.auth.dto.response.AuthResponseDto;
import com.sparta.spangeats.domain.auth.service.AuthService;
import com.sparta.spangeats.domain.auth.service.KakaoService;
import com.sparta.spangeats.security.config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;

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

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code,
                                             HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(token + "\t\t\t 로그인 성공");
    }

}
