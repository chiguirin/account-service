package com.bank.account.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountStatementResponseDTO(
        LocalDateTime date,
        String movementType,
        BigDecimal amount,
        BigDecimal balance,
        String accountNumber
) {
}