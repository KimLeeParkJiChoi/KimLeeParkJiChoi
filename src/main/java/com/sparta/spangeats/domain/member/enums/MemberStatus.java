package com.sparta.spangeats.domain.member.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE(Status.ACTIVE),
    DELETED(Status.DELETED);

    private final String status;

    public static class Status {
        public static final String ACTIVE = "ACTIVE";
        public static final String DELETED = "DELETED";
    }
}
