package com.highway.lottery.modules.ticket.service;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.modules.ticket.dto.TicketFilter;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest dto,String user);
    List<TicketResponse> getTickets();
    APIListResponse<SoldTicketResponse> getSoldTicketByAgentId(Long agentId,int pageNo,int pageSize,String sortBy,String sortDir);
    APISingleResponse<TicketResponse> getTicketByTicketCode(String ticketCode);
    TicketResponse getTicketDetailsForPdf(String ticketCode);

    boolean verifyTicket(String signature,String payload);
    // agent to find sold tickets under his name.

    // admin operation without ownership of sold ticket.
    APIListResponse<SoldTicketResponse> search(TicketFilter filter, Pageable pageable);
}
