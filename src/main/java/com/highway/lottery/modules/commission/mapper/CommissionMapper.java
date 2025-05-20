package com.highway.lottery.modules.commission.mapper;

import com.highway.lottery.common.mapper.IModelMapper;
import com.highway.lottery.modules.commission.dto.CommissionRequest;
import com.highway.lottery.modules.commission.dto.CommissionSummaryDTO;
import com.highway.lottery.modules.commission.entity.Commission;
import org.springframework.stereotype.Component;

@Component
public class CommissionMapper implements IModelMapper<CommissionRequest, CommissionSummaryDTO, Commission> {
    @Override
    public Commission toEntity(CommissionRequest data) {
        return null;
    }

    @Override
    public CommissionSummaryDTO toResponseDto(Commission entity) {
        return new CommissionSummaryDTO(
                entity.getId(),
                entity.getId(),
                entity.getTicket().getTicketCode(),
                entity.getAmount(),
                entity.getEarnedDate(),
                entity.getCommissionWithdrawal() != null ? entity.getCommissionWithdrawal().getId() : null
        );
    }

}
