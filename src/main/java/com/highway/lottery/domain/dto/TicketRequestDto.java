package com.highway.lottery.domain.dto;

import java.time.LocalDate;
import java.util.List;

public class TicketRequestDto {
    private String customerName;
    private String phone;
    private LocalDate drawDate;
    private String drawType;
    private List<TicketNumberDTO> numbers;
}
