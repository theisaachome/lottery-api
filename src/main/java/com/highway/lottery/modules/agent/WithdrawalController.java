package com.highway.lottery.modules.agent;
import com.highway.lottery.common.util.DateUtils;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import com.highway.lottery.modules.commission.service.CommissionWithdrawalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent/commissions")
public class WithdrawalController {


    private final CommissionWithdrawalService commissionWithdrawalService;

    public WithdrawalController(CommissionWithdrawalService commissionWithdrawalService) {
        this.commissionWithdrawalService = commissionWithdrawalService;
    }

    /*
        Request withdrawal
        Trigger a withdrawal request for commissions not yet withdrawn.
        Example: /agent/commissions/request-withdrawal — this could mark withdrawn = true, or add a status like PENDING if you implement withdrawal requests.
        */
    @PostMapping("/withdraw")
    public ResponseEntity<CommissionWithdrawal> requestWeeklyWithdrawal() {
        var result = commissionWithdrawalService.processWithdrawalRequest(DateUtils.getCurrentWeekStart(), DateUtils.getCurrentWeekEnd());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    /*

    View withdrawal history
    Dates, amounts, and ticket references for each withdrawal.

     */

}
