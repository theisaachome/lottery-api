package com.highway.lottery.modules.ticket.dto;

public sealed interface TicketVerificationResult permits TicketVerificationResponse,TicketVerificationErrorResponse {
}
