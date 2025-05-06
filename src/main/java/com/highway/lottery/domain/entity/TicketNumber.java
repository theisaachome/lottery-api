package com.highway.lottery.domain.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketNumber extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(nullable = false, length = 2)
    private String number;

    @Column(nullable = false)
    private BigDecimal amount;

    public TicketNumber(String number, BigDecimal amount, Ticket ticket) {
        this.number = number;
        this.amount = amount;
        this.ticket = ticket;
    }
}
