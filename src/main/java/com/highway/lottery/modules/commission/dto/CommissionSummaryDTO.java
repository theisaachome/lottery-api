package com.highway.lottery.modules.commission.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CommissionSummaryDTO(
        Long id,
        Long agentId,
        String ticketCode,
        BigDecimal amount,
        LocalDate earnedDate,
        Long withdrawalId) {
}
