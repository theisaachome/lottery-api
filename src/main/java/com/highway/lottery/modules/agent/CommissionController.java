package com.highway.lottery.modules.agent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent/commissions")
public class CommissionController {

    //# View earned commissions
    @GetMapping
    public ResponseEntity<String> getAllCommissions() {
        return  new ResponseEntity<>("Get All Commission", HttpStatus.OK);
    }
}
