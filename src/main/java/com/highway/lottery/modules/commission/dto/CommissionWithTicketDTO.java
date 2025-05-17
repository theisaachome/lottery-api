package com.highway.lottery.modules.commission.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommissionWithTicketDTO{
    private Long id;
    private BigDecimal amount;
    private LocalDate earnedDate;
    private boolean withdrawn;
    private TicketSummaryDTO ticket;

    public CommissionWithTicketDTO(Long id, BigDecimal amount, LocalDate earnedDate, boolean withdrawn, TicketSummaryDTO ticket) {
        this.id = id;
        this.amount = amount;
        this.earnedDate = earnedDate;
        this.withdrawn = withdrawn;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getEarnedDate() {
        return earnedDate;
    }

    public void setEarnedDate(LocalDate earnedDate) {
        this.earnedDate = earnedDate;
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public TicketSummaryDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketSummaryDTO ticket) {
        this.ticket = ticket;
    }
}
