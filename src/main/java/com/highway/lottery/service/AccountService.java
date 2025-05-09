package com.highway.lottery.service;
import com.highway.lottery.domain.dto.AccountRequest;
import com.highway.lottery.domain.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    AccountResponse getAccountDetails(Long accountId);
    AccountResponse updateAccount(Long accountId,AccountRequest request);
    void deleteAccount(Long accountId);// soft-delete
    List<AccountResponse> getAllAccounts();
}
