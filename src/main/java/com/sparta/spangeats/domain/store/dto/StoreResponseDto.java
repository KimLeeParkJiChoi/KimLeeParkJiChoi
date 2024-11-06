package com.sparta.spangeats.domain.store.dto;

import com.sparta.spangeats.domain.store.entity.Store;
import com.sparta.spangeats.domain.store.enums.StoreStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record StoreResponseDto(
        Long id,
        Long memberId,
        String name,
        LocalTime openTime,
        LocalTime closeTime,
        Long minOrderPrice,
        String phoneNumber,
        String address,
        StoreStatus status, // 상태 (OPEN 또는 CLOSED)
        String notice, // 공지 추가
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {


    // Store 엔터티 -> StoreResponseDto 변환 메서드
    public static StoreResponseDto from(Store store) {
        return new StoreResponseDto(
                store.getId(),
                store.getMember().getId(),
                store.getName(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderPrice(),
                store.getPhoneNumber(),
                store.getAddress(),
                store.getStatus(),
                store.getNotice(), // 공지 추가
                store.getCreatedAt(),
                store.getUpdatedAt()
        );
    }
}
