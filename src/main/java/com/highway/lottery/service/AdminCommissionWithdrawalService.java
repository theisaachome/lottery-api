package com.highway.lottery.service;
import com.highway.lottery.common.dto.CommissionWithdrawalDTO;
import com.highway.lottery.common.enums.WithdrawalStatus;
import java.util.List;

public interface AdminCommissionWithdrawalService {
    // 1. View all withdrawal requests (can add pagination/filtering)
    List<CommissionWithdrawalDTO> getAllWithdrawalRequests();

    // 2. View withdrawal requests by status (e.g., PENDING)
    List<CommissionWithdrawalDTO> getWithdrawalsByStatus(WithdrawalStatus status);

    // 3. Approve a withdrawal request
    void approveWithdrawal(Long withdrawalId, String approvedBy);

    // 4. Reject a withdrawal request with reason
    void rejectWithdrawal(Long withdrawalId, String rejectedBy, String reason);

    // 5. View withdrawal details
    CommissionWithdrawalDTO getWithdrawalDetails(Long withdrawalId);

    // 6. Optional: Mark as failed (e.g., transfer error)
    void markWithdrawalAsFailed(Long withdrawalId, String failedReason);
}
