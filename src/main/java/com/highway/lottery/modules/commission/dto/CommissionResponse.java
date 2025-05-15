package com.highway.lottery.modules.commission.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CommissionResponse(
        Long id,
        Long agentId,
        Long ticketId,
        BigDecimal amount,
        LocalDate earnedDate,
        Long withdrawalId) {
}
