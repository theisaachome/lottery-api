package com.highway.lottery.modules.ticket.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TicketVerificationResponse(
        String status,
        String ticketCode,
        String agentCode,
        LocalDateTime soldAt,
        Double totalAmount,
        List<TicketNumberDto> ticketNumbers
) {
}
