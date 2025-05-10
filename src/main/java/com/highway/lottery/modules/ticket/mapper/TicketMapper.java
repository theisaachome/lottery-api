package com.highway.lottery.modules.ticket.mapper;
import com.highway.lottery.common.mapper.IModelMapper;
import com.highway.lottery.modules.ticket.dto.TicketNumberRequest;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.entity.Ticket;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;


@Component
public class TicketMapper implements IModelMapper<TicketRequest, TicketResponse, Ticket> {

    @Override
    public Ticket toEntity(TicketRequest data) {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(data.getCustomerName());
        ticket.setPhone(data.getPhone());
        ticket.setDrawDate(LocalDate.now());
        ticket.setDrawType(data.getDrawType());
        var totalAmount = data.getNumbers()
                .stream()
                .map(TicketNumberRequest::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        ticket.setTotalAmount(totalAmount);

        return ticket;
    }

    @Override
    public TicketResponse toResponseDto(Ticket ticket) {
        var ticketNumbers = ticket.getTicketNumbers()
                .stream().map((data)->new TicketNumberRequest(data.getNumber(), data.getAmount())).collect(Collectors.toList());
        return new TicketResponse(
                ticket.getId(),
                ticket.getCustomerName(),
                ticket.getPhone(),
                ticket.getDrawDate(),
                ticket.getDrawType(),
                ticket.getTotalAmount(),
//                ticket.getCommission(),
                ticket.getQrCodeUrl(),
                ticket.getCreatedAt(),
                ticketNumbers);
    }
}
