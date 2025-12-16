package com.bank.account.application.usecase;


import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.client.CustomerClient;
import com.bank.account.infrastructure.client.dto.CustomerStatusDTO;
import com.bank.account.infrastructure.controller.dto.AccountMovementReportResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateAccountMovementReportUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(GenerateAccountMovementReportUseCase.class);

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final CustomerClient customerClient;

    public GenerateAccountMovementReportUseCase(AccountRepository accountRepository,
                                                MovementRepository movementRepository,
                                                CustomerClient customerClient) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
        this.customerClient = customerClient;
    }

    public List<AccountMovementReportResponseDTO> execute(
            String customerId,
            LocalDateTime from,
            LocalDateTime to) {

        log.info("Generating movement report for customer {}", customerId);


        CustomerStatusDTO customer = customerClient.getCustomerStatus(customerId);
        String customerName = customer.customerId();


        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        List<AccountMovementReportResponseDTO> report = new ArrayList<>();

        for (Account account : accounts) {

            List<Movement> movements =
                    movementRepository.findByAccountNumberAndDateBetween(
                            account.getAccountNumber(),
                            from,
                            to
                    );


            for (Movement movement : movements) {

                report.add(new AccountMovementReportResponseDTO(
                        movement.getDate(),
                        customerId,
                        customerName,
                        account.getAccountNumber(),
                        account.getAccountType().name(),
                        account.getInitialBalance(),
                        account.isActive(),
                        movement.getAmount(),
                        movement.getBalanceAfter()
                ));
            }
        }

        log.info("Report generated with {} records", report.size());
        return report;
    }
}