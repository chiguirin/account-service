package com.bank.account.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerStatusDTO(
        @JsonProperty("id") String customerId,
        boolean active
) {}