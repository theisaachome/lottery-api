package com.highway.lottery.service;
import com.highway.lottery.domain.IModelMapper;
import com.highway.lottery.domain.dto.TicketRequestDto;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import com.highway.lottery.domain.entity.TicketNumber;
import com.highway.lottery.domain.mapper.TicketMapper;
import com.highway.lottery.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketResponseDTO createTicket(TicketRequestDto dto) {
        // get entity
        var entity = ticketMapper.toEntity(dto);
        var ticketNumbers =
                dto.getNumbers()
                        .stream()
                        .map(data->new TicketNumber(data.getNumber(),data.getAmount(),entity))
                        .collect(Collectors.toList());
        entity.setTicketNumbers(ticketNumbers);
        var savedEntity= ticketRepository.save(entity);
        return ticketMapper.toResponseDto(savedEntity);
    }

    @Override
    public List<TicketResponseDTO> getTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
