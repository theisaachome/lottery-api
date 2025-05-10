package com.highway.lottery.modules.ticket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketRequest {
    private String customerName;
    private String phone;
    private String drawType;
    private List<TicketNumberDto> numbers;
}
