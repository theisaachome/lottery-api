package com.highway.lottery.modules.agent;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.config.AppConstants;
import com.highway.lottery.config.security.SecurityUser;
import com.highway.lottery.modules.commission.service.CommissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agent/commissions")
public class CommissionController {

    private final CommissionService commissionService;
    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    //# View earned commissions (for authenticated agent)
    @GetMapping
    public ResponseEntity<APIListResponse> getAllEligibleCommissions(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int limit,
            @RequestParam(value = "sort", defaultValue = "earnedDate", required = false) String sort,
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String direction
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var authenticatedUser = (SecurityUser) auth.getPrincipal();
        Long userId = authenticatedUser.getAccount().getId();
        var result = this.commissionService.getAvailableCommission(userId,page, limit,sort, direction);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    View earned commissions (for authenticated agent)
     Filter by date, status (withdrawn or not)
     Example: /agent/commissions?startDate=2025-05-01&endDate=2025-05-13&commissionWithdrawal=null
     */
    @GetMapping("/{commissionId}")
     public ResponseEntity<APISingleResponse> getCommissionsByAgentId(@PathVariable("commissionId") Long commissionId) {
            var result = commissionService.getCommissionDetailsWithTicket(commissionId);
         return  new ResponseEntity<>(result, HttpStatus.OK);
     }

    /*
    View total commission summary
        Total earned
        Total withdrawn
        Total available for withdrawal
     */
}
