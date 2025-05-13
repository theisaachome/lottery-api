package com.highway.lottery.modules.ticket.service;
import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.common.exception.APIException;
import com.highway.lottery.common.exception.ResourceNotFoundException;
import com.highway.lottery.common.util.AppCodeGenerator;
import com.highway.lottery.common.util.TicketUtils;
import com.highway.lottery.config.security.SecurityUser;
import com.highway.lottery.modules.ticket.dto.TicketFilter;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.domain.entity.Commission;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;
import com.highway.lottery.modules.ticket.entity.Ticket;
import com.highway.lottery.modules.ticket.entity.TicketNumber;
import com.highway.lottery.modules.ticket.mapper.TicketMapper;
import com.highway.lottery.modules.commission.repo.CommissionRepository;
import com.highway.lottery.modules.ticket.repo.TicketRepository;
import com.highway.lottery.modules.ticket.repo.TicketSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketUtils ticketUtils;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CommissionRepository commissionRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, CommissionRepository commissionRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.commissionRepository = commissionRepository;
    }

    @Override
    public TicketResponse createTicket(TicketRequest dto,String user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        var agent = userDetails.getAccount(); // ← reuse, no new DB call
        // find seller
//        var agent = accountRepo.findAccountByUsername(user)
//                .orElseThrow(()->new UnauthorizedException("Authenticated user is not registered in the system."));

        // map to entity from dto
        var entity = ticketMapper.toEntity(dto);
        var ticketNumbers =
                dto.getNumbers()
                        .stream()
                        .map(data->new TicketNumber(data.getNumber(),data.getAmount(),entity))
                        .collect(Collectors.toList());
        entity.setAgent(agent);
        entity.setTicketNumbers(ticketNumbers);
        entity.setAgentCode(agent.getAgentCode());
        entity.setTicketCode(AppCodeGenerator.generateTicketCode(agent.getAgentCode()));

        var savedEntity= ticketRepository.save(entity);


        var commission = new Commission();
        commission.setAgent(commission.getCreatedBy());
        commission.setTicket(savedEntity);
        commission.setWithdrawn(false);
        // 10 % of total sale amount - to be confirmed
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


    @Override
    public APISingleResponse<TicketResponse> getTicketByTicketCode(String ticketCode) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var soldTicket = ticketRepository.findByTicketCodeAndAgentCodeWithDetails(ticketCode,username)
                .orElseThrow(()->new ResourceNotFoundException("No sold ticket found with ticket-code : " +ticketCode));
        return  new APISingleResponse<>(true,ticketMapper.toResponseDto(soldTicket),"Subject retrvie successfully");
    }

    @Override
    public TicketResponse getTicketDetailsForPdf(String ticketCode) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var soldTicket = ticketRepository.findByTicketCodeAndAgentCodeWithDetails(ticketCode,username)
                .orElseThrow(()->new ResourceNotFoundException("No sold ticket found with ticket-code : " +ticketCode));
        return  ticketMapper.toResponseDto(soldTicket);
    }


    @Override
    public boolean verifyTicket(String signature, String payload) {
        // Step 4: Check ticket existence
//        var payload = request.getPayload();
//        boolean exists = ticketRepository.existsByIdAndTicketCode(payload.getTicketId(), payload.getTicketCode());
        try {
            return ticketUtils.verifyTicket(signature,payload);
        }catch (Exception e){
            throw new APIException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @Override
    public APIListResponse<SoldTicketResponse> search(TicketFilter filter, Pageable pageable) {
        Specification<Ticket> spec = TicketSpecifications.withFilter(filter);
       Page<Ticket> ticketPage = ticketRepository.findAll(spec, pageable);
       List<SoldTicketResponse> soldTicketResponseList = ticketPage.getContent()
               .stream().map(ticketMapper::toSoldTicketResponse).collect(Collectors.toList());
        return new APIListResponse<>(
                true,
                new APIListResponse.Meta(
                        ticketPage.getNumber(),
                        ticketPage.getSize(),
                        ticketPage.getTotalPages(),
                        ticketPage.getTotalElements(),
                        ticketPage.isLast()),
                soldTicketResponseList,
                "Subjects retrieved successfully");
    }

    @Override
    public APIListResponse<SoldTicketResponse> getSoldTicketByAgentId(Long agentId,int pageNo,int pageSize,String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
       var soldTicketPage = ticketRepository.findSoldAllTicketsByAgentId(agentId,pageable);

        var soldTicketList= soldTicketPage.getContent()
                .stream()
                .map(ticketMapper::toSoldTicketResponse)
                .collect(Collectors.toList());

           return new APIListResponse<>(
                true,
                new APIListResponse.Meta(
                        soldTicketPage.getNumber(),
                        soldTicketPage.getSize(),
                        soldTicketPage.getTotalPages(),
                        soldTicketPage.getTotalElements(),
                        soldTicketPage.isLast()),
                   soldTicketList,
                "Subjects retrieved successfully");
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
