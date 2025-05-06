package com.highway.lottery.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TicketRequestDto {
    private String customerName;
    private String phone;
    private LocalDate drawDate;
    private String drawType;
    private List<TicketNumberDTO> numbers;
}
