package com.sparta.spangeats.domain.store.dto;

import com.sparta.spangeats.domain.store.enums.StoreStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record StoreResponseDto(
        Long id,
        String memberId,
        Long name,
        LocalTime openTime,
        LocalTime closeTime,
        Long minOrderPrice,
        String phoneNumber,
        String address,
        StoreStatus status, // 상태 (OPEN 또는 CLOSED)
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
