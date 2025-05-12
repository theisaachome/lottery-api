package com.highway.lottery.modules.ticket.service;

import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest dto,String user);
    List<TicketResponse> getTickets();

    List<TicketResponse> getTicketsByAgentId(Long agentId);
    List<SoldTicketResponse> getSoldTicketByAgentId(Long agentId);
    TicketResponse getTicketByTicketCode(String ticketCode);
    boolean verifyTicket(String signature,String payload);
}
