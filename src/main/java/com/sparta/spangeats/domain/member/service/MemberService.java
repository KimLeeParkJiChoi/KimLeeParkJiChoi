package com.sparta.spangeats.domain.member.service;

import com.sparta.spangeats.domain.auth.dto.request.LoginRequestDto;
import com.sparta.spangeats.domain.auth.dto.response.AuthResponseDto;
import com.sparta.spangeats.domain.auth.exception.AuthException;
import com.sparta.spangeats.domain.member.dto.SignoutRequestDto;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.exception.MemberException;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.security.config.CustomPasswordEncoder;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final CustomPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

//    public AuthResponseDto login(LoginRequestDto requestDto) {
//        Member member = memberRepository.findByEmail(requestDto.email())
//                .orElseThrow(() -> new AuthException("가입되지 않은 유저입니다."));
//
//        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
//            throw new AuthException("잘못된 비밀번호입니다.");
//        }
//
//        String bearerToken = jwtUtil.createToken(member.getEmail(), member.getMemberRole());
//
//        return new AuthResponseDto(bearerToken);
//    }

    @Transactional
    public void signout(SignoutRequestDto requestDto, UserDetailsImpl userDetails) {

        if (!passwordEncoder.matches(requestDto.password(), userDetails.getPassword())) {
            throw new MemberException("잘못된 비밀번호입니다.");
        }

        memberRepository.deleteByEmail(userDetails.getUsername());
    }
}
