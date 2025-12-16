package com.bank.account.application.usecase;


import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.controller.dto.AccountStatementResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class GenerateAccountStatementUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(GenerateAccountStatementUseCase.class);

    private final MovementRepository movementRepository;

    public GenerateAccountStatementUseCase(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<AccountStatementResponseDTO> execute(
            String accountNumber,
            LocalDateTime from,
            LocalDateTime to) {

        log.info("Generating account statement report for account {}", accountNumber);

        List<Movement> movements =
                movementRepository.findByAccountNumberAndDateBetween(
                        accountNumber, from, to
                );

        return movements.stream()
                .map(m -> new AccountStatementResponseDTO(
                        m.getDate(),
                        m.getMovementType().name(),
                        m.getAmount(),
                        m.getBalanceAfter(),
                        m.getAccountNumber()
                ))
                .toList();
    }
}