package com.sparta.spangeats.domain.member.service;

import com.sparta.spangeats.domain.member.dto.request.SignoutRequestDto;
import com.sparta.spangeats.domain.member.dto.request.UpdateMemberRequestDto;
import com.sparta.spangeats.domain.member.dto.response.AdminMemberInfoResponse;
import com.sparta.spangeats.domain.member.dto.response.MemberInfoResponseDto;
import com.sparta.spangeats.domain.member.entity.Member;
import com.sparta.spangeats.domain.member.enums.MemberStatus;
import com.sparta.spangeats.domain.member.exception.MemberException;
import com.sparta.spangeats.domain.member.repository.MemberRepository;
import com.sparta.spangeats.security.config.CustomPasswordEncoder;
import com.sparta.spangeats.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                userDetails.getMemberRole(),
                userDetails.getCreartedAt(),
                userDetails.getUpdatedAt(),
                userDetails.getMember().getKakaoId()
        );
    }

    public List<AdminMemberInfoResponse> getAllMemberInfo(UserDetailsImpl userDetails, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.findAll(pageable).stream()
                .map(member -> new AdminMemberInfoResponse(
                        member.getEmail(),
                        member.getPassword(),
                        member.getNickname(),
                        member.getPhoneNumber(),
                        member.getMemberRole(),
                        member.getMemberStatus(),
                        member.getCreatedAt(),
                        member.getUpdatedAt(),
                        member.getKakaoId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMemberInfo(UpdateMemberRequestDto requestDto, UserDetailsImpl userDetails) {

        if (!passwordEncoder.matches(requestDto.currentPassword(), userDetails.getPassword())) {
            throw new MemberException("현재 비밀번호가 일치하지 않습니다.");
        }

        Member member = userDetails.getMember();

        if (!member.isValidNickName(requestDto.nickname(), userDetails.getNickname())) {
            throw new MemberException("이미 사용 중인 닉네임입니다.");
        }

        setMemberFields(requestDto, member);

        memberRepository.save(member);
    }

    private void setMemberFields(UpdateMemberRequestDto requestDto, Member member) {
        if (requestDto.nickname() != null) {
            member.setNickname(requestDto.nickname());
        }

        if (requestDto.phoneNumber() != null) {
            member.setPhoneNumber(requestDto.phoneNumber());
        }

        if (requestDto.newPassword() != null) {
            member.setPassword(passwordEncoder.encode(requestDto.newPassword()));
        }
    }
}
