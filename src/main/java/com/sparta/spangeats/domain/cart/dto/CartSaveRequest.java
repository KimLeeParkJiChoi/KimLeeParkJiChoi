package com.sparta.spangeats.domain.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartSaveRequest(
        @NotNull(message = "menuId가 비어있습니다. 입력해주세요")
        Long menuId,
        @NotNull(message = "storeId가 비어있습니다. 입력해주세요")
        Long storeId,
        @Min(value = 1, message = "수량은 최소 1개여야 합니다.")
        Long quantity,
        @Positive(message = "가격은 양수입니다. 재입력해주세요!")
        @NotNull(message = "price가 비어있습니다.")
        Long price
) {
}
