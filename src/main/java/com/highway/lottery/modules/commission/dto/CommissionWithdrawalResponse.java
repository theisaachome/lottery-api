package com.highway.lottery.modules.commission.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CommissionWithdrawalResponse(
        boolean status,
        BigDecimal withdrawnAmount,
        LocalDate from,
        LocalDate to
) {
}
