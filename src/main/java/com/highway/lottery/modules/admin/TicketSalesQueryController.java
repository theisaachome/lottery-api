package com.highway.lottery.modules.admin;

import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;
import com.highway.lottery.modules.ticket.dto.TicketFilter;
import com.highway.lottery.modules.ticket.service.TicketService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/sales")
public class TicketSalesQueryController {

    private final TicketService ticketService;

    public TicketSalesQueryController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // # Query all sales
    @GetMapping("/search")
    public ResponseEntity<APIListResponse<SoldTicketResponse>> searchTicket(
            @ModelAttribute TicketFilter filter,
            @PageableDefault(size = 10, sort = "drawDate", direction = Sort.Direction.DESC) Pageable pageable
    ){
        var result = ticketService.search(filter,pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
