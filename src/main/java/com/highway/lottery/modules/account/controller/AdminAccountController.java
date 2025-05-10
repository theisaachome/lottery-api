package com.highway.lottery.modules.account.controller;

import com.highway.lottery.modules.account.dto.AccountRequest;
import com.highway.lottery.modules.account.dto.AccountResponse;
import com.highway.lottery.modules.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/accounts")
public class AdminAccountController {

    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // admin-role => create account for agent
    @PreAuthorize("hasRole(ADMIN)")
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest dto){
        var newAccount = accountService.createAccount(dto);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
    // update the agent account by Admin-role
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("accountId")Long accountId, @Valid @RequestBody AccountRequest dto){
        var newAccount = accountService.updateAccount(accountId, dto);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAccount(){
        var accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    // get account details
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountDetails(@PathVariable("accountId")Long accountId){
        var account = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    // delete (soft-delete) account (agent-account)
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountId")Long accountId){
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>("Account deleted",HttpStatus.OK);
    }
    // update account role.

}
