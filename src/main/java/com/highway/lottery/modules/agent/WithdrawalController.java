package com.highway.lottery.modules.agent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent/withdrawal")
public class WithdrawalController {


    /*
    Request withdrawal
    Trigger a withdrawal request for commissions not yet withdrawn.
    Example: /agent/commissions/request-withdrawal — this could mark withdrawn = true, or add a status like PENDING if you implement withdrawal requests.


    View withdrawal history
    Dates, amounts, and ticket references for each withdrawal.

     */

}
