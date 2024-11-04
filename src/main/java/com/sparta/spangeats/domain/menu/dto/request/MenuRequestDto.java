package com.sparta.spangeats.domain.menu.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MenuRequestDto(
        @NotNull(message = "가게 ID는 필수입니다.") Long storeId,

        @NotBlank(message = "메뉴 이름은 필수입니다.")
        @Size(max = 20, message = "메뉴 이름은 20자 이하여야 합니다.") String name,

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.") Integer price) {
}
