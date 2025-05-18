package com.highway.lottery.modules.admin;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.modules.commission.service.CommissionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/admin/commissions")
public class CommissionOverviewController {


    private final CommissionService commissionService;

    public CommissionOverviewController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }
    //# View all commissions

    @GetMapping
    public ResponseEntity<APIListResponse> getCommissionOverview(
            @RequestParam(required = false) Long agentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) Boolean withdrawn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        var result = this.commissionService.searchCommissions(agentId, start, end, withdrawn, page, size);
        return ResponseEntity.ok(result);
    }

    /*
    🧑‍💼 Admin Role (Superuser):
    Admins manage agents and process withdrawal requests. They can approve, reject, and monitor all commission activity.

✅ Common Admin Operations:
    View all agents' commissions

    View commissions per agent, ticket, and time range.

    Filter by withdrawal status.

    View pending withdrawals

    Commissions flagged as ready for withdrawal or requested by agents.

    Approve or reject withdrawal

    Update withdrawn = true and set withdrawDate = LocalDate.now() upon approval.

    Alternatively, add a status column (e.g., PENDING, APPROVED, REJECTED) for more robust workflows.

    Manual withdrawal processing

    Mark commissions as withdrawn manually in case of errors or offline handling.

            Generate reports

    Commission reports by agent, period, or ticket.

    Export to Excel or PDF.

            Audit trails

    Track who processed or updated withdrawal records (optional: add audit columns like updatedBy).

     */
}
