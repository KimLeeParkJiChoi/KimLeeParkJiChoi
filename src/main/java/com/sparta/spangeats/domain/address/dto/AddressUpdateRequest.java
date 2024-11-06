package com.sparta.spangeats.domain.address.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressUpdateRequest(
        @NotBlank(message = "주소가 비어있습니다.")
        String address
) {
}
