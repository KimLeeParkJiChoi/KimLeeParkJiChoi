package com.sparta.spangeats.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record StoreRequestDto(
        @NotBlank(message = "가게 이름은 필수입니다.")
        @Size(min = 1, max = 50, message = "가게 이름은 1자 이상 50자 이하로 입력해야 합니다.")
        String name,

        @NotNull(message = "오픈 시간은 필수입니다.")
        LocalTime openTime,

        @NotNull(message = "마감 시간은 필수입니다.")
        LocalTime closeTime,

        @NotNull(message = "최소 금액은 필수입니다.")
        Long minOrderPrice,

        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(min = 10, max = 15, message = "전화번호는 10자 이상 15자 이하로 입력해야 합니다.")
        String phoneNumber,

        @NotBlank(message = "주소는 필수입니다.")
        @Size(max = 100, message = "주소는 100자 이하로 입력해야 합니다.")
        String address
) {}
