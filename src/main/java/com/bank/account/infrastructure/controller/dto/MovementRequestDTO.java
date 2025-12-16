package com.bank.account.infrastructure.controller.dto;

import java.math.BigDecimal;

public record MovementRequestDTO(
        String accountNumber,
        BigDecimal amount
) {}