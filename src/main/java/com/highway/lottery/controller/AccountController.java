package com.highway.lottery.controller;

import com.highway.lottery.domain.dto.AccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/accounts")
public class AccountController {

    // admin create account for agent
    @PostMapping
    public ResponseEntity<String> createAccount(
            @RequestBody AccountRequestDTO dto
            ){
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }
    // delete (soft-delete) account (agent-account)
}
