package com.highway.lottery.domain.dto;

public record AccountRequestDTO(
        String username,
        String phone,
        String password
) {
}
