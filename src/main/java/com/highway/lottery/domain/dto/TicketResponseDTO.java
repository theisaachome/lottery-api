package com.highway.lottery.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDTO {
    private UUID id;
    private String customerName;
    private String phone;
    private LocalDate drawDate;
    private String drawType;
    private BigDecimal totalAmount;
//    private BigDecimal commission;
    private String qrCodeUrl;
    private LocalDateTime createdAt;
    private List<TicketNumberDTO> numbers;
}
