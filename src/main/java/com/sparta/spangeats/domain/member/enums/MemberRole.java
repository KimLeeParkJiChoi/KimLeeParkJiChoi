package com.sparta.spangeats.domain.member.enums;

import com.sparta.spangeats.domain.auth.exception.AuthException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum MemberRole {
    USER(Authority.USER),
    OWNER(Authority.OWNER),
    ADMIN(Authority.ADMIN);

    private final String authority;


    public static MemberRole of(String memberRole) {
        return Arrays.stream(MemberRole.values())
                .filter(r -> r.name().equalsIgnoreCase(memberRole))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 MemberRole : " + memberRole));
    }

    /*
    요청된 역할을 기반으로 사용자 역할을 반환하는 메서드
    만약 요청된 역할이 ADMIN이라면 제공된 관리자 키가 유효한지 검증하고 ADMIN 역할을 반환
     */
    public static MemberRole from(String memberRole, String adminKey, String providedKey) {
        if (ADMIN.name().equalsIgnoreCase(memberRole)) {
            if(!adminKey.equals(providedKey)) {
                throw new AuthException("유효하지 않은 관리자 키입니다.");
            }
            return ADMIN;
        }
        return MemberRole.of(memberRole);
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "USER";
        public static final String OWNER = "OWNER";
        public static final String ADMIN = "ADMIN";
    }
}
