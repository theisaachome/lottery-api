package com.highway.lottery.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    private String customerName;

    private String phone;

    @Column(nullable = false)
    private LocalDate drawDate;

    @Column(nullable = false)
    private String drawType;

    @Column(nullable = false)
    private BigDecimal totalAmount;

//    @Column(nullable = false)
    private BigDecimal commission;

    private String qrCodeUrl;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketNumber> ticketNumbers;
}
