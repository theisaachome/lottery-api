package com.highway.lottery.domain.mapper;
import com.highway.lottery.domain.IModelMapper;
import com.highway.lottery.domain.dto.AccountRequest;
import com.highway.lottery.domain.dto.AccountResponse;
import com.highway.lottery.domain.entity.Account;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountMapper implements IModelMapper<AccountRequest, AccountResponse, Account> {

    private final PasswordEncoder passwordEncoder;

    public AccountMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account toEntity(AccountRequest data) {
        var newAccount = new Account();
        newAccount.setUsername(data.username());
        newAccount.setPhone(data.phone());
        newAccount.setPassword(passwordEncoder.encode(data.password()));
        return newAccount;
    }

    @Override
    public AccountResponse toResponseDto(Account account) {
        var roles = account.getRoles().stream().map((r)->r.getRoleName()).collect(Collectors.toList());
        return new AccountResponse(account.getUsername(), account.getPhone(),
                 roles,
                "Account Created Successfully",
                account.getCreatedBy(), account.getUpdatedBy(),
                account.getActive(),account.getCreatedAt(), account.getUpdatedAt());
    }
}
