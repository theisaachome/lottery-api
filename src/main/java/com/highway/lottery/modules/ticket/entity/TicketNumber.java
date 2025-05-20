package com.highway.lottery.modules.ticket.entity;
import com.highway.lottery.common.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@BatchSize(size = 20)
public class TicketNumber extends BaseEntity {


    @Column(nullable = false, length = 2)
    private String number;

    @Column(nullable = false)
    private BigDecimal amount;

    public TicketNumber(String number, BigDecimal amount, Ticket ticket) {
        this.number = number;
        this.amount = amount;
        this.ticket = ticket;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}
