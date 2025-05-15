package com.highway.lottery.modules.commission.service;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.CommissionWithdrawalDTO;
import com.highway.lottery.modules.commission.dto.CommissionResponse;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import com.highway.lottery.modules.commission.mapper.CommissionMapper;
import com.highway.lottery.modules.commission.repo.CommissionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public CommissionWithdrawal requestWithdrawal(String agentId) {
        return null;
    }

    @Override
    public List<CommissionWithdrawalDTO> getWithdrawalHistory(String agentId) {
        return List.of();
    }

    @Override
    public APIListResponse<CommissionResponse> getAvailableCommission(Long agentId, int page, int limit, String sort, String direction) {
        Sort sortBy = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(page, limit, sortBy);
        var commissionsPage = commissionRepository.findByAgentId(agentId,pageable);


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
    public CommissionWithdrawalDTO getWithdrawalDetails(Long withdrawalId, String agentId) {
        return null;
    }
}
