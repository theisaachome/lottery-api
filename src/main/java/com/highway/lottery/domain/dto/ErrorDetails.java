package com.highway.lottery.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Integer statusCode;
}
