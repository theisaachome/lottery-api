package com.highway.lottery.modules.commission.service;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.common.dto.CommissionWithdrawalDTO;
import com.highway.lottery.modules.commission.dto.CommissionSummaryDTO;
import com.highway.lottery.modules.commission.dto.CommissionWithTicketDTO;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;

import java.time.LocalDate;
import java.util.List;

public interface CommissionService {
    // 1. Agent requests withdrawal for available commissions
//    CommissionWithdrawal requestWithdrawal(String agentId);

    // 2. Agent views their withdrawal history
//    List<CommissionWithdrawalDTO> getWithdrawalHistory(String agentId);

    // 3. Agent views current available commissions
    APIListResponse<CommissionSummaryDTO> getAvailableCommission(Long agentId, int page, int limit, String sort, String direction);

    // 4. Optional: View details of a single withdrawal
//    CommissionWithdrawalDTO getWithdrawalDetails(Long withdrawalId, String agentId);

    APISingleResponse<CommissionWithTicketDTO> getCommissionDetailsWithTicket(Long commissionId);

    APIListResponse<CommissionSummaryDTO> searchCommissions(    Long agentId,
                                                                     LocalDate startDate,
                                                                     LocalDate endDate,
                                                                     Boolean withdrawn,
                                                                     int page,
                                                                     int size);
}
