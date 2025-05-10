package com.highway.lottery.modules.ticket.entity;

import com.highway.lottery.common.dto.BaseEntity;
import com.highway.lottery.modules.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private String customerName;

    private String phone;

    @Column(name = "ticket_code", nullable = false, unique = true, length = 40)
    private String ticketCode;
    private String agentCode;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate drawDate;

    @Column(nullable = false)
    private String drawType;

    @Column(nullable = false)
    private BigDecimal totalAmount;


    private String qrCodeUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Account agent;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketNumber> ticketNumbers;
}
