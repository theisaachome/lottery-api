package com.highway.lottery.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CommissionItemDTO {
    private Long ticketId;
    private String customerName;
    private LocalDate drawDate;
    private BigDecimal ticketAmount;
    private BigDecimal commissionEarned;
}
