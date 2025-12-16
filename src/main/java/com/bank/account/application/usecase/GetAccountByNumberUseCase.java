package com.bank.account.application.usecase;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.exception.BusinessException;
import com.bank.account.infrastructure.controller.dto.AccountResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GetAccountByNumberUseCase {

    private final AccountRepository repository;

    public GetAccountByNumberUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public AccountResponseDTO execute(String accountNumber) {
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found"));

        return new AccountResponseDTO(
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getAccountType().name(),
                account.getCurrentBalance(),
                account.isActive()
        );
    }
}