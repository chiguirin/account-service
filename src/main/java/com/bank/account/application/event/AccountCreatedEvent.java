package com.bank.account.application.event;


import java.time.LocalDateTime;

public class AccountCreatedEvent {

    private String accountNumber;
    private String customerId;
    private LocalDateTime createdAt;

    public AccountCreatedEvent() {
    }

    public AccountCreatedEvent(String accountNumber, String customerId, LocalDateTime createdAt) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}