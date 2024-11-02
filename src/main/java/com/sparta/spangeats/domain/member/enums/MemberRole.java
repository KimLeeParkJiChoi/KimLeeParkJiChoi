package com.sparta.spangeats.domain.member.enums;

import java.util.Arrays;

public enum MemberRole {
    USER, OWNER;

    public static MemberRole of(String memberrole) {
        return Arrays.stream(MemberRole.values())
                .filter(r -> r.name().equalsIgnoreCase(memberrole))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 MemberRole : " + memberrole));
    }
}
