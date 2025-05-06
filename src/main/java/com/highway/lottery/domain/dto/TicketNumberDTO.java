package com.highway.lottery.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketNumberDTO {
    private String customerName;
    private String phone;
    private LocalDate drawDate;
    private String drawType;
    private List<TicketNumberDTO> numbers;
}
