package com.highway.lottery.modules.ticket.service;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.modules.ticket.dto.TicketFilter;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest dto,String user);
    List<TicketResponse> getTickets();

    List<TicketResponse> getTicketsByAgentId(Long agentId);
    List<SoldTicketResponse> getSoldTicketByAgentId(Long agentId);
    TicketResponse getTicketByTicketCode(String ticketCode);
    boolean verifyTicket(String signature,String payload);

    APIListResponse<SoldTicketResponse> search(TicketFilter filter, Pageable pageable);
}
