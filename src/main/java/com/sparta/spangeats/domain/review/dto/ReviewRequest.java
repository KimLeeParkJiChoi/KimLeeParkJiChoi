package com.sparta.spangeats.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @Max(5) @Min(1)
        Long score,
        String contents
) {
}
