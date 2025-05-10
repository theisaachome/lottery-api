package com.highway.lottery.modules.agent;

import com.highway.lottery.common.exception.UnauthorizedException;
import com.highway.lottery.modules.account.repo.AccountRepo;
import com.highway.lottery.modules.ticket.dto.SoldTicketResponse;
import com.highway.lottery.modules.ticket.dto.TicketRequest;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.highway.lottery.modules.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent/tickets")
//@PreAuthorize("hasRole('AGENT')")
public class TicketSellingController {
    private final TicketService ticketService;
    private final AccountRepo accountRepo;

    public TicketSellingController(TicketService ticketService, AccountRepo accountRepo) {
        this.ticketService = ticketService;
        this.accountRepo = accountRepo;
    }

    // create lottery-ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createLotteryTicket(@RequestBody TicketRequest dto, Authentication authentication) {
        var result = ticketService.createTicket(dto,authentication.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // get All sold ticket
//    @GetMapping
//    public ResponseEntity<List<TicketResponse>> getAllTicketSales(Authentication authentication){
//        var results = ticketService.getTicketsByAgentId(getLoggedUser(authentication.getName()));
//        return new ResponseEntity<>(results,HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<SoldTicketResponse>> getSoldTicketSales(Authentication authentication){
        var result = ticketService.getSoldTicketByAgentId(getLoggedUser(authentication.getName()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TicketResponse>> getTicketSalesHistory(){
        var results = ticketService.getTickets();
        return new ResponseEntity<>(results,HttpStatus.OK);
    }

//    @GetMapping(value = "/{ticketCode}/download", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> downloadTicketPdf(@PathVariable String ticketCode) throws Exception {
//        String agentCode = "AGENT001"; // Fetch from ticket or logged-in user
//        byte[] pdf = TicketPdfGenerator.generateTicketPdf(ticketCode, agentCode);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.builder("attachment")
//                .filename("ticket_" + ticketCode + ".pdf")
//                .build());
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }
    private Long getLoggedUser(String username) {
        var user = accountRepo.findAccountByUsername(username)
                .orElseThrow(()->new UnauthorizedException("Authenticated user is not registered in the system."));
        return user.getId();
    }


}
