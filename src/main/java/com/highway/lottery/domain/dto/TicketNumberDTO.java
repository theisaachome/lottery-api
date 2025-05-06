package com.highway.lottery.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketNumberDTO {
    private String number; // e.g. "12"
    private BigDecimal amount;
}
