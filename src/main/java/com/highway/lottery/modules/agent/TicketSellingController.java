package com.highway.lottery.modules.agent;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.common.exception.UnauthorizedException;
import com.highway.lottery.common.util.TicketPdfGenerator;
import com.highway.lottery.modules.account.repo.AccountRepo;
import com.highway.lottery.modules.ticket.dto.*;
import com.highway.lottery.modules.ticket.service.TicketService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agent/tickets")
public class TicketSellingController {
    private final TicketService ticketService;
    private final AccountRepo accountRepo;
    private final TicketPdfGenerator ticketPdfGenerator;

    public TicketSellingController(TicketService ticketService, AccountRepo accountRepo, TicketPdfGenerator ticketPdfGenerator) {
        this.ticketService = ticketService;
        this.accountRepo = accountRepo;
        this.ticketPdfGenerator = ticketPdfGenerator;
    }
    // POST /api/tickets
    // create lottery-ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createLotteryTicket(@RequestBody TicketRequest dto, Authentication authentication) {
        var result = ticketService.createTicket(dto,authentication.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //  GET /api/tickets/{ticketCode}
    //→ View ticket details by ticket code, ensuring it belongs to the authenticated agent.
    @GetMapping("/{ticketCode}")
    public ResponseEntity<APISingleResponse> getTicketDetailByTicketCode(@PathVariable("ticketCode") String ticketCode){
        var result = ticketService.getTicketByTicketCode(ticketCode);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }

    // GET /api/v1/agent/tickets/export (Optional)
    // Export a list of their sold tickets to CSV or PDF format for reporting.

    // GET /api/v1/agent/tickets
    // List all tickets sold by the authenticated agent
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

    @GetMapping("/{ticketCode}/preview")
    public ResponseEntity<byte[]> previewTicket(@PathVariable("ticketCode")String ticketCode){
        byte[] pdfBytes = ticketPdfGenerator.generateTicketPdf(ticketService.getTicketDetailsForPdf(ticketCode));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("ticket_invoice.pdf").build());
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
    // /tickets/verify
    @GetMapping("/verify")
    public ResponseEntity<TicketVerificationResult> verifyTicket(
            @RequestParam("signature") String signature,
            @RequestParam("payload")String payload){
        var isMatched = ticketService.verifyTicket(signature,payload);
        if(!isMatched){
            return ResponseEntity.status(401).body(new TicketVerificationResult("INVALID","Invalid Ticket"));
        }
        return ResponseEntity.ok().body(new TicketVerificationResult("VALID","Success"));
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
