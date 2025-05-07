package com.highway.lottery.domain.entity;

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
public class Commission extends BaseEntity{


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "agent_id", nullable = false)
    private String agent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate earnedDate;

    @Column(nullable = false)
    private boolean withdrawn = false;


    @UpdateTimestamp
    @Column(name = "withdraw_date")
    private LocalDate withdrawDate;
}
