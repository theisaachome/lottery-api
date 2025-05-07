package com.highway.lottery.service;
import com.highway.lottery.domain.dto.TicketRequestDTO;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import com.highway.lottery.domain.entity.Commission;
import com.highway.lottery.domain.entity.TicketNumber;
import com.highway.lottery.domain.mapper.TicketMapper;
import com.highway.lottery.repository.CommissionRepository;
import com.highway.lottery.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CommissionRepository commissionRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, CommissionRepository commissionRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.commissionRepository = commissionRepository;
    }

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO dto) {
        // get entity
        var entity = ticketMapper.toEntity(dto);
        var ticketNumbers =
                dto.getNumbers()
                        .stream()
                        .map(data->new TicketNumber(data.getNumber(),data.getAmount(),entity))
                        .collect(Collectors.toList());
        entity.setTicketNumbers(ticketNumbers);

        var savedEntity= ticketRepository.save(entity);

        var commission = new Commission();
        commission.setAgent(commission.getCreatedBy());
        commission.setTicket(savedEntity);
        commission.setWithdrawn(false);
        commission.setAmount(getCommission(entity.getTotalAmount(),BigDecimal.valueOf(0.10)));
        commissionRepository.save(commission);
        return ticketMapper.toResponseDto(savedEntity);
    }

    @Override
    public List<TicketResponseDTO> getTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    private BigDecimal getCommission(BigDecimal totalSale,BigDecimal commissionRate) {
        BigDecimal commission = BigDecimal.ZERO;
        if (totalSale != null && commissionRate != null) {
        commission= commission.add(totalSale.multiply(commissionRate).setScale(2, BigDecimal.ROUND_HALF_UP));
        return commission;
        }
        return commission;
    }
}
