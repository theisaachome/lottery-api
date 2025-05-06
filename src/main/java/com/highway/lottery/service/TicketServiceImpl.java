package com.highway.lottery.service;
import com.highway.lottery.domain.IModelMapper;
import com.highway.lottery.domain.dto.TicketRequestDto;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import com.highway.lottery.domain.mapper.TicketMapper;
import com.highway.lottery.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final IModelMapper iModelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.iModelMapper = ticketMapper;
    }

    @Override
    public TicketResponseDTO createTicket(TicketRequestDto dto) {
        // get entity
        var entity = iModelMapper.toEntity(dto);

        return null;
    }

    @Override
    public List<TicketResponseDTO> getTickets() {
        return List.of();
    }
}
