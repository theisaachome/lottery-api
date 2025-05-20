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

    // Agent operation
    TicketResponse createTicket(TicketRequest dto,String user);
    // to verify sold ticket
    boolean verifyTicket(String signature,String payload);
    // agent to find sold tickets under his name.
    APIListResponse<SoldTicketResponse> getSoldTicketByAgentId(Long agentId,int pageNo,int pageSize,String sortBy,String sortDir);
    // get ticket details by id
    APISingleResponse<TicketResponse> getTicketById(Long ticketId);
    // response object different data for pdf content
    TicketResponse getTicketDetailsForPdf(String ticketCode);

    // Admin Operation
    // get All ticket sold for time to timme.
    List<TicketResponse> getTickets();

    APISingleResponse<TicketResponse> getTicketByTicketCode(String ticketCode);

    // admin operation without ownership of sold ticket.
    APIListResponse<SoldTicketResponse> search(TicketFilter filter, Pageable pageable);
}
