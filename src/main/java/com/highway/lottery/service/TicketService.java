package com.highway.lottery.service;

import com.highway.lottery.domain.dto.TicketRequestDTO;
import com.highway.lottery.domain.dto.TicketResponseDTO;

import java.util.List;

public interface TicketService {

    TicketResponseDTO createTicket(TicketRequestDTO dto);
    List<TicketResponseDTO> getTickets();
}
