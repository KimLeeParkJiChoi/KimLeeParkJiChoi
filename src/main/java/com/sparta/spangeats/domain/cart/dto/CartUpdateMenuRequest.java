package com.sparta.spangeats.domain.cart.dto;

import jakarta.validation.constraints.Min;

public record CartUpdateMenuRequest(
        @Min(value = 1, message = "수량은 최소 1개여야 합니다.")
        Long quantity
) {
}
