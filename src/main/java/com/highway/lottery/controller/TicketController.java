package com.highway.lottery.controller;
import com.highway.lottery.domain.dto.TicketRequestDto;
import com.highway.lottery.domain.dto.TicketResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    // create lottery-ticket
    @PostMapping
    public ResponseEntity<TicketResponseDTO> createLotteryTicket(@RequestBody TicketRequestDto dto) {
        return new ResponseEntity<>(new TicketResponseDTO(),HttpStatus.OK);
    }
}
