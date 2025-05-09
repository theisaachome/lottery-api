package com.highway.lottery.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Phone is required")
        String phone,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Account must be associated with a role. Role is required")
        String role
) {
}
