package com.highway.lottery.service.impl;

import com.highway.lottery.domain.dto.AccountRequest;
import com.highway.lottery.domain.dto.AccountResponse;
import com.highway.lottery.domain.entity.Account;
import com.highway.lottery.domain.mapper.AccountMapper;
import com.highway.lottery.exception.ResourceNotFoundException;
import com.highway.lottery.repository.AccountRepo;
import com.highway.lottery.repository.RoleRepository;
import com.highway.lottery.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    private final RoleRepository roleRepository;

    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper, RoleRepository roleRepository) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        var entity=accountMapper.toEntity(request);
        var roles = roleRepository.findByRoleName(request.role())
                .orElseThrow(()->new ResourceNotFoundException("Role not found for request"));
        entity.setRoles(Set.of(roles));
       var savedAccount= accountRepo.save(entity);
        return  accountMapper.toResponseDto(savedAccount);
    }

    @Override
    public AccountResponse getAccountDetails(Long accountId) {
        var account = accountRepo.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException("Account ","ID",accountId));
        return accountMapper.toResponseDto(account);
    }

    @Override
    public AccountResponse updateAccount(Long accountId, AccountRequest request) {
        var account = accountRepo.findById(accountId)
                        .orElseThrow(()->new ResourceNotFoundException("Account ","ID",accountId));
        account.setUsername(request.username());
        account.setPhone(request.phone());
        var updatedAccount=accountRepo.save(account);
        return accountMapper.toResponseDto(updatedAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        var account=accountRepo.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException("Account ","ID",accountId));
        account.setActive(false);
        accountRepo.save(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        var accounts = accountRepo.findAll()
                .stream()
                .map(accountMapper::toResponseDto)
                .collect(Collectors.toList());
        return accounts;
    }
}
