package com.highway.lottery.modules.commission.service;

import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;

import java.time.LocalDate;

public interface CommissionWithdrawalService {

    CommissionWithdrawal processWithdrawalRequest( LocalDate from, LocalDate to);

}
