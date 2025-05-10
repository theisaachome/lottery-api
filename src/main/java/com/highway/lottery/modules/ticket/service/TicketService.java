package com.highway.lottery.modules.ticket.service;

import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest dto);
    List<TicketResponse> getTickets();
}
