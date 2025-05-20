package com.highway.lottery.common.enums;

public enum WithdrawalStatus {
    PENDING,       // Requested but not yet processed
    APPROVED,    // Being reviewed or processed
    REJECTED,     // Successfully paid out
    PAID,
}
