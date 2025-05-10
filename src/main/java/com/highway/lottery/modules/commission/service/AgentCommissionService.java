package com.highway.lottery.modules.commission.service;

import com.highway.lottery.common.dto.CommissionWithdrawalDTO;
import com.highway.lottery.domain.entity.CommissionWithdrawal;

import java.math.BigDecimal;
import java.util.List;

public interface AgentCommissionService {
    // 1. Agent requests withdrawal for available commissions
    CommissionWithdrawal requestWithdrawal(String agentId);

    // 2. Agent views their withdrawal history
    List<CommissionWithdrawalDTO> getWithdrawalHistory(String agentId);

    // 3. Agent views current available commission balance
    BigDecimal getAvailableCommission(String agentId);

    // 4. Optional: View details of a single withdrawal
    CommissionWithdrawalDTO getWithdrawalDetails(Long withdrawalId, String agentId);
}
