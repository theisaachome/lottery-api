package com.highway.lottery.modules.ticket.dto;

public record TicketVerificationErrorResponse(
        String status,
        String message
) {
}
