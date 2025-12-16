package com.bank.account.infrastructure.controller;


import com.bank.account.application.service.AccountService;
import com.bank.account.domain.model.AccountType;
import com.bank.account.infrastructure.controller.dto.AccountResponseDTO;
import com.bank.account.infrastructure.controller.dto.CreateAccountRequestDTO;
import com.bank.account.infrastructure.controller.dto.UpdateAccountStatusRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts", description = "Account operations")
@RestController
@RequestMapping("/cuentas")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateAccountRequestDTO request) {

        AccountType accountType = AccountType.valueOf(request.accountType());

        service.createAccount(
                request.accountNumber(),
                request.customerId(),
                accountType,
                request.initialBalance(),
                request.active()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDTO> getByAccountNumber(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(service.getAccountByNumber(accountNumber));
    }

    @PatchMapping("/{accountNumber}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String accountNumber,
            @RequestBody UpdateAccountStatusRequestDTO request) {

        service.updateAccountStatus(accountNumber, request.active());

        return ResponseEntity.noContent().build();
    }

}
