package com.highway.lottery.modules.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SoldTicketResponse(
        Long ticketId,
        String ticketCode,
        String agentCode,
        LocalDateTime createdAt,
        List<TicketNumberDto> ticketNumbers,
        BigDecimal totalAmount
        ) {}
