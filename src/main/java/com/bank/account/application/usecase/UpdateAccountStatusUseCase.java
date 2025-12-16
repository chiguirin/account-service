package com.bank.account.application.usecase;


import com.bank.account.domain.model.Account;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UpdateAccountStatusUseCase {

    private final AccountRepository repository;

    public UpdateAccountStatusUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(String accountNumber, Boolean active) {

        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found"));

        account.setActive(active);
    }
}