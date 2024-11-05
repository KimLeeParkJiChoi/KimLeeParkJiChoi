package com.sparta.spangeats.domain.member.service;

import com.sparta.spangeats.domain.member.dto.request.SignoutRequestDto;
import com.sparta.spangeats.domain.member.dto.response.MemberInfoResponseDto;
import com.sparta.spangeats.domain.member.enums.MemberStatus;
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

    @Transactional
    public void signout(SignoutRequestDto requestDto, UserDetailsImpl userDetails) {

        if (!passwordEncoder.matches(requestDto.password(), userDetails.getPassword())) {
            throw new MemberException("사용자 아이디와 비빌번호가 일치하지 않습니다.");
        }

        if (userDetails.getMemberStatus().equals(MemberStatus.DELETED)) {
            throw new MemberException("이미 탈퇴한 사용자입니다.");
        }


        userDetails.getMember().setMemberStatus(MemberStatus.DELETED);
        memberRepository.save(userDetails.getMember());
    }

    public MemberInfoResponseDto getMemberInfo(UserDetailsImpl userDetails) {

        if (userDetails.getMember().getMemberStatus().equals(MemberStatus.DELETED)) {
            throw new MemberException("이미 탈퇴한 사용자입니다.");
        }

        return new MemberInfoResponseDto(
                userDetails.getEmail(),
                userDetails.getNickname(),
                userDetails.getPhoneNumber(),
                userDetails.getMemberRole()
        );
    }
}
