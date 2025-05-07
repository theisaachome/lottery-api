package com.highway.lottery.domain.dto;

import com.highway.lottery.domain.entity.WithdrawalStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class CommissionWithdrawalDTO { private Long id;
    private BigDecimal requestedAmount;        // Amount requested for withdrawal
    private BigDecimal approvedAmount;         // Final approved amount (if different)
    private LocalDate requestDate;             // Date the agent requested withdrawal
    private LocalDate processedDate;           // Date admin processed it
    private WithdrawalStatus status;           // e.g., PENDING, COMPLETED, etc.
    private String remarks;                    // Admin remarks (rejected reason, etc.)

    private List<CommissionItemDTO> items;     // Breakdown of commissions involved

    private String bankAccountName;            // Name registered for bank account
    private String bankName;                   // Bank name (optional, if recorded)
    private String bankAccountNumber;          // Last 4 digits or masked version

    private Boolean isWeeklyPayout;            // true if it's a system-generated weekly payout
}
