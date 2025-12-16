package com.bank.account.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountMovementReportResponseDTO(

        LocalDateTime fecha,
        String customerId,
        String cliente,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        boolean estadoCuenta,
        BigDecimal movimiento,
        BigDecimal saldoDisponible

) {}