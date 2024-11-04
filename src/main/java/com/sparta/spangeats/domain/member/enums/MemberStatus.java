package com.sparta.spangeats.domain.member.enums;

import com.sparta.spangeats.domain.member.exception.MemberException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE(Status.ACTIVE),
    DELETED(Status.DELETED);

    private final String status;

    public static MemberStatus of(String status) {
        return Arrays.stream(MemberStatus.values())
                .filter(r -> r.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new MemberException("유효하지 않은 사용자 Status : " + status));
    }

    public String getStatus() {
        return this.status;
    }

    public static class Status {
        public static final String ACTIVE = "ACTIVE";
        public static final String DELETED = "DELETED";
    }
}
