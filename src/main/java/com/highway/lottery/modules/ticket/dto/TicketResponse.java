package com.highway.lottery.modules.ticket.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String ticketCode;
    private String agentCode;
    private String customerName;
    private String phone;
    private LocalDate drawDate;
    private String drawType;
    private BigDecimal totalAmount;
    private String qrCodeUrl;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<TicketNumberDto> ticketNumbers;
}
