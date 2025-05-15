package com.highway.lottery.modules.commission.entity;

import com.highway.lottery.common.dto.BaseEntity;
import com.highway.lottery.modules.account.entity.Account;
import com.highway.lottery.modules.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "commissions")
public class Commission extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Account agent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate earnedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdrawal_id")
     private CommissionWithdrawal commissionWithdrawal; // nullable - null = not withdrawn
}
