package com.highway.lottery.controller;
import com.highway.lottery.domain.dto.TicketRequestDTO;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import com.highway.lottery.service.TicketService;
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
    public ResponseEntity<TicketResponseDTO> createLotteryTicket(@RequestBody TicketRequestDTO dto) {
        var result = ticketService.createTicket(dto);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTicketSales(){
        var results = ticketService.getTickets();
        return new ResponseEntity<>(results,HttpStatus.OK);
    }
}
