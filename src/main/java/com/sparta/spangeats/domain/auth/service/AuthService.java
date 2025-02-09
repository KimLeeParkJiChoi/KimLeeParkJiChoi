package com.sparta.spangeats.domain.auth.service;

import com.sparta.spangeats.domain.auth.dto.request.LoginRequestDto;
import com.sparta.spangeats.domain.auth.dto.request.SignupRequestDto;
import com.sparta.spangeats.domain.auth.dto.response.AuthResponseDto;
import com.sparta.spangeats.domain.auth.exception.AuthException;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.security.config.CustomPasswordEncoder;
import com.sparta.spangeats.security.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Value("${admin.secret.key}")
    private String adminKey;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.email())) {
            throw new AuthException("이미 존재하는 이메일, 또는 회원 탈퇴한 이메일입니다.");
        }

        if (memberRepository.existsByNickname(requestDto.nickname())) {
            throw new AuthException("이미 존재하는 닉네임 입니다.");
        }

        MemberRole memberRole = MemberRole.from(
                requestDto.memberRole(),
                adminKey,
                requestDto.adminSecretKey()
        );

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        Member member = new Member(requestDto.email(),
                encodedPassword,
                requestDto.nickname(),
                memberRole,
                requestDto.phoneNumber()
        );
        memberRepository.save(member);
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new AuthException("가입되지 않은 유저입니다."));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(member.getEmail(), member.getMemberRole());

        return new AuthResponseDto(bearerToken);
    }
}
