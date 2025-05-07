package com.highway.lottery.domain.entity;

public enum WithdrawalStatus {
    PENDING,       // Requested but not yet processed
    PROCESSING,    // Being reviewed or processed
    COMPLETED,     // Successfully paid out
    FAILED,        // Failed due to payment gateway or system error
    REJECTED,      // Rejected by admin (e.g., invalid request)
    CANCELLED      // Cancelled by agent or system before processing
}
