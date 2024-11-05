package com.sparta.spangeats.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequestDto(
        @NotBlank
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "비밀번호는 최소 8자리 이상이며, 대소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다."
        )
        String password,

        @NotBlank
        String nickname,

        @NotBlank
        String memberRole,

        @NotBlank
        String phoneNumber,

        String adminSecretKey
) {
}
