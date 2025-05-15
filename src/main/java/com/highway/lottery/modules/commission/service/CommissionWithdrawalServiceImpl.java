package com.highway.lottery.modules.commission.service;

import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommissionWithdrawalServiceImpl implements CommissionWithdrawalService{
    @Override
    public CommissionWithdrawal processWithdrawalRequest(Long agentId, LocalDate from, LocalDate to) {
        // get all commissions by agent with given date range must be

        return null;
    }
}
