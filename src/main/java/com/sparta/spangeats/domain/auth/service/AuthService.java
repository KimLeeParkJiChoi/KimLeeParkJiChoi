package com.sparta.spangeats.domain.auth.service;

import com.sparta.spangeats.domain.auth.dto.request.SignupRequestDto;
import com.sparta.spangeats.domain.auth.dto.response.LoginResponseDto;
import com.sparta.spangeats.domain.auth.exception.AuthException;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.security.config.CustomPasswordEncoder;
import com.sparta.spangeats.security.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public LoginResponseDto signup(SignupRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.email())) {
            throw new AuthException("이미 존재하는 이메일 입니다.");
        }

        if (memberRepository.existsByNickname(requestDto.nickname())) {
            throw new AuthException("이미 존재하는 닉네임 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        MemberRole memberRole = MemberRole.of(requestDto.memberRole());

        // 사용자 등록
        Member member = new Member(requestDto.email(),
                encodedPassword,
                requestDto.nickname(),
                memberRole,
                requestDto.phoneNumber());
        memberRepository.save(member);

        String bearerToken = jwtUtil.createToken(member.getEmail(), member.getMemberRole());

        return new LoginResponseDto(bearerToken);
    }

}
