package com.bank.account.infrastructure.controller.dto;

import java.math.BigDecimal;


public record CreateAccountRequestDTO(
        String accountNumber,
        String customerId,
        String accountType,
        BigDecimal initialBalance,
        boolean active
) {}