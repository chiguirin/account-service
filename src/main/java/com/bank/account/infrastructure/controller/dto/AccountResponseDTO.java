package com.bank.account.infrastructure.controller.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
        String accountNumber,
        String customerId,
        String accountType,
        BigDecimal balance,
        Boolean active
) {}