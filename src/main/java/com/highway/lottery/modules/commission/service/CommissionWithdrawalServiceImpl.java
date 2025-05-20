package com.highway.lottery.modules.commission.service;

import com.highway.lottery.common.enums.WithdrawalStatus;
import com.highway.lottery.common.exception.APIException;
import com.highway.lottery.config.security.SecurityUser;
import com.highway.lottery.modules.commission.entity.Commission;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import com.highway.lottery.modules.commission.repo.CommissionRepository;
import com.highway.lottery.repository.CommissionWithdrawalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CommissionWithdrawalServiceImpl implements CommissionWithdrawalService{
    private final CommissionRepository commissionRepository;
    private final CommissionWithdrawalRepository commissionWithdrawalRepository;

    public CommissionWithdrawalServiceImpl(CommissionRepository commissionRepository, CommissionWithdrawalRepository commissionWithdrawalRepository) {
        this.commissionRepository = commissionRepository;
        this.commissionWithdrawalRepository = commissionWithdrawalRepository;
    }

    @Transactional
    @Override
    public CommissionWithdrawal processWithdrawalRequest(LocalDate from, LocalDate to) {
        // get authenticate user
        SecurityUser authenticatedUser =(SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // get all commissions by agent with given date range must be
        var eligibleCommissions = commissionRepository.findCommissionByAgentAndDateRange(authenticatedUser
                .getAccount().getId(),from,to);
        if (eligibleCommissions.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST,"No eligible commissions found for the given date range.");
        }
       BigDecimal withdrawalAmount = eligibleCommissions.
               stream()
               .map(Commission::getAmount)
               .reduce(BigDecimal.ZERO, BigDecimal::add);

       var newWithdrawal = new CommissionWithdrawal();
       newWithdrawal.setAgent(authenticatedUser.getAccount());
        newWithdrawal.setTotalAmount(withdrawalAmount);
        newWithdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
        newWithdrawal.setWithdrawalDate(LocalDate.now());
        newWithdrawal.setCommissionList(new ArrayList<>());
        for(var commission : eligibleCommissions) {
            commission.setCommissionWithdrawal(newWithdrawal);
            newWithdrawal.getCommissionList().add(commission);
        }

        return commissionWithdrawalRepository.save(newWithdrawal);
    }
}
