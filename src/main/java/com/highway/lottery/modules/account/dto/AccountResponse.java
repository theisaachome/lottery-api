package com.highway.lottery.modules.account.dto;
import java.time.LocalDateTime;
import java.util.List;

public record AccountResponse(
        String username,
        String phone,
        List roles,
        String message,
        String createdBy,
        String updatedBy,
        boolean status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
