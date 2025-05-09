package com.highway.lottery.domain.dto;

public record JwtResponse(
        String token,
        String message
) {
}
