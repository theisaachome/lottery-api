package com.highway.lottery.modules.ticket.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketNumberDto {
    private String number; // e.g. "12"
    private BigDecimal amount;
}
