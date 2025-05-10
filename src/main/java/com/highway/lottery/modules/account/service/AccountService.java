package com.highway.lottery.modules.account.service;
import com.highway.lottery.modules.account.dto.AccountRequest;
import com.highway.lottery.modules.account.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    AccountResponse getAccountDetails(Long accountId);
    AccountResponse updateAccount(Long accountId,AccountRequest request);
    void deleteAccount(Long accountId);// soft-delete
    List<AccountResponse> getAllAccounts();
}
