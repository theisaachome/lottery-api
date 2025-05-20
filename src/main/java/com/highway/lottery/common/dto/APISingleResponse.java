package com.highway.lottery.common.dto;

public record APISingleResponse<T>(
        boolean success,
        T data,
        String message
) {
}
