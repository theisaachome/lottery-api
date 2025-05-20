package com.highway.lottery.modules.commission.service;
import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.common.exception.ResourceNotFoundException;
import com.highway.lottery.modules.commission.dto.CommissionSummaryDTO;
import com.highway.lottery.modules.commission.dto.CommissionWithTicketDTO;
import com.highway.lottery.modules.commission.entity.Commission;
import com.highway.lottery.modules.commission.mapper.CommissionMapper;
import com.highway.lottery.modules.commission.repo.CommissionRepository;
import com.highway.lottery.modules.commission.repo.CommissionSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepository commissionRepository;
    private final CommissionMapper commissionMapper;

    public CommissionServiceImpl(CommissionRepository commissionRepository, CommissionMapper commissionMapper) {
        this.commissionRepository = commissionRepository;
        this.commissionMapper = commissionMapper;
    }

    @Override
    public APIListResponse<CommissionSummaryDTO> getAvailableCommission(Long agentId, int page, int limit, String sort, String direction) {
        Sort sortBy = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(page, limit, sortBy);
        var commissionsPage = commissionRepository.findAllCommissionAvailableByAgentId(agentId,pageable);


         return  new APIListResponse<>(
                 true,
                new APIListResponse.Meta(
                        commissionsPage.getNumber(),
                        commissionsPage.getSize(),
                        commissionsPage.getTotalPages(),
                        commissionsPage.getTotalElements(),
                        commissionsPage.isLast()),
                commissionsPage.getContent(),
                 "Subject retrieved successfully"
         );
    }

    @Override
    public APISingleResponse<CommissionWithTicketDTO> getCommissionDetailsWithTicket(Long commissionId) {
        var result = commissionRepository.findCommissionWithTicketByCommissionId(commissionId)
                .orElseThrow(()->new ResourceNotFoundException("Commission","ID",commissionId));

        return new APISingleResponse<>(true,
                result,"Subject retrieved successfully");
    }

    @Override
    public APIListResponse<CommissionSummaryDTO> searchCommissions(Long agentId, LocalDate startDate, LocalDate endDate, Boolean withdrawn, int page, int size) {
        Specification<Commission> spec = Specification
                .where(CommissionSpecifications.hasAgent(agentId))
                .and(CommissionSpecifications.earnedBetween(startDate, endDate))
                .and(CommissionSpecifications.isWithdrawn(withdrawn));
        Pageable pageable = PageRequest.of(page, size, Sort.by("earnedDate").descending());
        var commissionsPage = commissionRepository.findAll(spec, pageable);

        return  new APIListResponse<>(
                true,
                new APIListResponse.Meta(
                        commissionsPage.getNumber(),
                        commissionsPage.getSize(),
                        commissionsPage.getTotalPages(),
                        commissionsPage.getTotalElements(),
                        commissionsPage.isLast()),
                commissionsPage.getContent().stream().map(commissionMapper::toResponseDto)
                        .collect(Collectors.toList()),
                "Subject retrieved successfully"
        );
    }
}
