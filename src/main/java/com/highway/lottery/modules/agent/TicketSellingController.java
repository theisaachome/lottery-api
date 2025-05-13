package com.highway.lottery.modules.agent;
import com.highway.lottery.common.dto.APIListResponse;
import com.highway.lottery.common.dto.APISingleResponse;
import com.highway.lottery.common.util.TicketPdfGenerator;
import com.highway.lottery.config.AppConstants;
import com.highway.lottery.config.security.SecurityUser;
import com.highway.lottery.modules.ticket.dto.*;
import com.highway.lottery.modules.ticket.service.TicketService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agent/tickets")
public class TicketSellingController {
    private final TicketService ticketService;
    private final TicketPdfGenerator ticketPdfGenerator;

    public TicketSellingController(TicketService ticketService, TicketPdfGenerator ticketPdfGenerator) {
        this.ticketService = ticketService;
        this.ticketPdfGenerator = ticketPdfGenerator;
    }
    // POST /api/tickets
    // create lottery-ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createLotteryTicket(@RequestBody TicketRequest dto, Authentication authentication) {
        var result = ticketService.createTicket(dto,authentication.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //  GET /api/tickets/{ticketId}
    //→ View ticket details by ticket id, common operation.
    @GetMapping("/{ticketId}")
    public ResponseEntity<APISingleResponse> getTicketDetailsById(@RequestParam("ticketId") Long ticketId) {
        var result = ticketService.getTicketById(ticketId);
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
    public ResponseEntity<APIListResponse<SoldTicketResponse>> getSoldTicketSales(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int limit,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sort,
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String direction
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var authenticatedUser = (SecurityUser) auth.getPrincipal();
        Long userId = authenticatedUser.getAccount().getId();
        var result = ticketService.getSoldTicketByAgentId(userId,page,limit,sort,direction);
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


}
