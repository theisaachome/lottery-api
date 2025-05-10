package com.highway.lottery.modules.ticket.controller;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // create lottery-ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createLotteryTicket(@RequestBody TicketRequest dto) {
        var result = ticketService.createTicket(dto);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTicketSales(){
        var results = ticketService.getTickets();
        return new ResponseEntity<>(results,HttpStatus.OK);
    }
}
