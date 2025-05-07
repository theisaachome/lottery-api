package com.highway.lottery.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/commission-withdrawals")
public class AdminCommissionWithdrawalController {
    // GET /list - List all withdrawal requests
    // PUT /{id}/approve or /{id}/reject
    // GET /{id} - View specific request
}
