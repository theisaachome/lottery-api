package com.highway.lottery.modules.commission.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TicketSummaryDTO{
    private Long id;
    private String ticketCode;
    private BigDecimal totalAmount;
    private LocalDate drawDate;
    private String drawType;

    public TicketSummaryDTO(Long id, String ticketCode, BigDecimal totalAmount,
                            LocalDate drawDate, String drawType) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.totalAmount = totalAmount;
        this.drawDate = drawDate;
        this.drawType = drawType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public String getDrawType() {
        return drawType;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }
}
