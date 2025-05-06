package com.highway.lottery.domain.mapper;

import com.highway.lottery.domain.IModelMapper;
import com.highway.lottery.domain.dto.TicketNumberDTO;
import com.highway.lottery.domain.dto.TicketRequestDto;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import com.highway.lottery.domain.entity.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TicketMapper implements IModelMapper<TicketRequestDto, TicketResponseDTO, Ticket> {

    @Override
    public Ticket toEntity(TicketRequestDto data) {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(data.getCustomerName());
        ticket.setPhone(data.getPhone());
        ticket.setDrawDate(LocalDate.now());
        ticket.setDrawType(data.getDrawType());
        var totalAmount = data.getNumbers()
                .stream()
                .map(TicketNumberDTO::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        ticket.setTotalAmount(totalAmount);

        return ticket;
    }

    @Override
    public TicketResponseDTO toResponseDto(Ticket ticket) {
        return null;
    }
}
