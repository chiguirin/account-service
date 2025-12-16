package com.bank.account.domain.model;



import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "active", nullable = false)
    private boolean active;

    protected Account() {}

    public Account(String accountNumber,
                   String customerId,
                   AccountType accountType,
                   BigDecimal initialBalance,
                   boolean active) {

        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.currentBalance = initialBalance;
        this.active = active;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public boolean isActive() {
        return active;
    }


    public void applyMovement(BigDecimal amount) {
        this.currentBalance = this.currentBalance.add(amount);
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}