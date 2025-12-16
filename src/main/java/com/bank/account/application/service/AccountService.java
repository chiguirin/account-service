package com.bank.account.application.service;


import com.bank.account.application.usecase.GetAccountByNumberUseCase;
import com.bank.account.application.usecase.UpdateAccountStatusUseCase;
import com.bank.account.domain.model.*;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.exception.BusinessException;
import com.bank.account.infrastructure.client.CustomerClient;
import com.bank.account.infrastructure.client.dto.CustomerStatusDTO;
import com.bank.account.infrastructure.controller.dto.AccountResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final CustomerClient customerClient;
    private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    private final UpdateAccountStatusUseCase updateAccountStatusUseCase;

    public AccountService(AccountRepository accountRepository,
                          MovementRepository movementRepository,
                          CustomerClient customerClient, GetAccountByNumberUseCase getAccountByNumberUseCase, UpdateAccountStatusUseCase updateAccountStatusUseCase) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
        this.customerClient = customerClient;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.updateAccountStatusUseCase = updateAccountStatusUseCase;
    }

    // =========================
    // CREATE ACCOUNT
    // =========================
    public void createAccount(String accountNumber,
                              String customerId,
                              AccountType accountType,
                              BigDecimal initialBalance,
                              boolean active){

        log.info("Starting account creation for customer {}", customerId);

        CustomerStatusDTO customer = customerClient.getCustomerStatus(customerId);

        if (!customer.active()) {
            log.warn("Customer {} is not active", customerId);
            throw new BusinessException("Customer is not active");
        }

        if (accountRepository.existsById(accountNumber)) {
            throw new BusinessException("Account already exists");
        }

        Account account = new Account(
                accountNumber,
                customerId,
                accountType,
                initialBalance,
                active
        );

        accountRepository.save(account);

        log.info("Account {} created for customer {}", accountNumber, customerId);
    }

    // =========================
    // REGISTER MOVEMENT
    // =========================
    public void registerMovement(String accountNumber, BigDecimal amount) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found"));

        if (!account.isActive()) {
            log.warn("Account {} is inactive", accountNumber);
            throw new BusinessException("Account is inactive");
        }

        MovementType movementType =
                amount.compareTo(BigDecimal.ZERO) >= 0
                        ? MovementType.DEPOSITO
                        : MovementType.RETIRO;

        if (movementType == MovementType.RETIRO &&
                account.getCurrentBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {

            log.warn("Balance not enough for movement");
            throw new BusinessException("Saldo no disponible");
        }

        account.applyMovement(amount);

        Movement movement = new Movement(
                account.getAccountNumber(),
                movementType,
                amount,
                account.getCurrentBalance()
        );

        movementRepository.save(movement);
        accountRepository.save(account);

        log.info("Movement was registered successfuly");
    }

    // =========================
    // REPORT BASE
    // =========================
    public List<Account> getAccountsByCustomer(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public List<Movement> getMovements(String accountNumber,
                                       LocalDateTime from,
                                       LocalDateTime to) {

        return movementRepository.findByAccountNumberAndDateBetween(
                accountNumber,
                from,
                to
        );
    }
    // =========================
    // OBTENER CUENTA
    // =========================
    public AccountResponseDTO getAccountByNumber(String accountNumber) {
        return getAccountByNumberUseCase.execute(accountNumber);
    }
    // =========================
    // STATUS DE LA CUENTA
    // =========================
    public void updateAccountStatus(String accountNumber, Boolean active) {
        updateAccountStatusUseCase.execute(accountNumber, active);
    }
}