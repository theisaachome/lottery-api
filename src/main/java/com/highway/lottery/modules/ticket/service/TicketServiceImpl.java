package com.highway.lottery.modules.ticket.service;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.domain.entity.Commission;
import com.highway.lottery.modules.ticket.entity.TicketNumber;
import com.highway.lottery.modules.ticket.mapper.TicketMapper;
import com.highway.lottery.modules.commission.repo.CommissionRepository;
import com.highway.lottery.modules.ticket.repo.TicketRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CommissionRepository commissionRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, CommissionRepository commissionRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.commissionRepository = commissionRepository;
    }

    @Override
    public TicketResponse createTicket(TicketRequest dto) {
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
    public List<TicketResponse> getTickets() {
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
