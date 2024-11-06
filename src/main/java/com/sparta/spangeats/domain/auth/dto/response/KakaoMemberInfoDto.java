package com.sparta.spangeats.domain.auth.dto.response;

public record KakaoMemberInfoDto(
        Long id,
        String nickname,
        String email
) {
}
