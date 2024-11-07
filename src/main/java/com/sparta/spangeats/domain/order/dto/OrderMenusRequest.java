package com.sparta.spangeats.domain.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderMenusRequest(
        @NotNull(message = "menuId가 비어있습니다.")
        Long menuId,
        @NotNull(message = "수량이 비어있습니다.")
        Long quantity
) {
}
