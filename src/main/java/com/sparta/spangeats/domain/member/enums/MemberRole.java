package com.sparta.spangeats.domain.member.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum MemberRole {
    USER(Authority.USER),
    ONWER(Authority.OWNER);

    private final String authority;


    public static MemberRole of(String memberRole) {
        return Arrays.stream(MemberRole.values())
                .filter(r -> r.name().equalsIgnoreCase(memberRole))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 MemberRole : " + memberRole));
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "USER";
        public static final String OWNER = "OWNER";
    }
}
