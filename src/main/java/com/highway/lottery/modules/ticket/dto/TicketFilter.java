package com.highway.lottery.modules.ticket.dto;

import java.time.LocalDate;

public record TicketFilter(
        String ticketCode,
        String agentCode,
        String customerName,
        String phone,
        String drawType,
        LocalDate drawDateFrom,
        LocalDate drawDateTo
) {
}
