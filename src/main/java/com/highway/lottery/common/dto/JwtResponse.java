package com.highway.lottery.common.dto;

public record JwtResponse(
        String token,
        String message
) {
}
