package com.sparta.spangeats.domain.member.dto.response;

import com.sparta.spangeats.domain.member.enums.MemberRole;
import com.sparta.spangeats.domain.member.enums.MemberStatus;

import java.time.LocalDateTime;

public record AdminMemberInfoResponse(
        String email,
        String password,
        String nickname,
        String phoneNumber,
        MemberRole memberRole,
        MemberStatus memberStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
