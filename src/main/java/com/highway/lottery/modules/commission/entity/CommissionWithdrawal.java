package com.highway.lottery.modules.commission.entity;
import com.highway.lottery.common.dto.BaseEntity;
import com.highway.lottery.common.enums.WithdrawalStatus;
import com.highway.lottery.modules.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "commission_withdrawals")
public class CommissionWithdrawal extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Account agent;


    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false) // should be actual withdraw date not request date
    private LocalDate withdrawalDate;


    @Enumerated(EnumType.STRING)
    private WithdrawalStatus withdrawalStatus;

    // one to many (one-withdraw has many commissions)
    @OneToMany
    @JoinTable(
            name = "commission_withdrawal_items",
            joinColumns = @JoinColumn(name = "withdrawal_id"),
            inverseJoinColumns = @JoinColumn(name = "commission_id")
    )
    private List<Commission> commissionList;
}
