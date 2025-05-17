package com.highway.lottery.modules.commission.mapper;

import com.highway.lottery.common.mapper.IModelMapper;
import com.highway.lottery.modules.commission.dto.CommissionRequest;
import com.highway.lottery.modules.commission.dto.CommissionResponse;
import com.highway.lottery.modules.commission.entity.Commission;
import org.springframework.stereotype.Component;

@Component
public class CommissionMapper implements IModelMapper<CommissionRequest, CommissionResponse, Commission> {
    @Override
    public Commission toEntity(CommissionRequest data) {
        return null;
    }

    @Override
    public CommissionResponse toResponseDto(Commission entity) {
        return new CommissionResponse(
                entity.getId(),
                entity.getId(),
                entity.getTicket().getId(),
                entity.getAmount(),
                entity.getEarnedDate(),
                entity.getCommissionWithdrawal() != null ? entity.getCommissionWithdrawal().getId() : null
        );
    }

}
