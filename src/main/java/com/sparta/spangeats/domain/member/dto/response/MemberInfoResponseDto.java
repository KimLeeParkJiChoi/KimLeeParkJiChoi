package com.sparta.spangeats.domain.member.dto.response;

import com.sparta.spangeats.domain.member.enums.MemberRole;

public record MemberInfoResponseDto(String email,
                                    String nickname,
                                    String phoneNumber,
                                    MemberRole memberRole) {
}
