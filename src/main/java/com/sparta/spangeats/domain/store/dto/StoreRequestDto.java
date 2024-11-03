package com.sparta.spangeats.domain.store.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record StoreRequestDto(
        @NotBlank(message = "가게 이름은 필수입니다.")
        String name,

        @NotBlank(message = "오픈 시간은 필수입니다.")
        LocalTime openTime,

        @NotBlank(message = "마감 시간은 필수입니다.")
        LocalTime closeTime,

        @NotBlank(message = "최소 주문 가격은 필수입니다.")
        Long minOrderPrice,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phoneNumber,

        @NotBlank(message = "주소는 필수입니다.")
        String address
) {}
