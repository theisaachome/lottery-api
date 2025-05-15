package com.highway.lottery.modules.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/commissions")
public class CommissionOverviewController {

    //# View all commissions

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
