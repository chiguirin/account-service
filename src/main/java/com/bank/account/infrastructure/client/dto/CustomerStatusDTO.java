package com.bank.account.infrastructure.client.dto;

public record CustomerStatusDTO(
        String customerId,
        boolean active
) {}
