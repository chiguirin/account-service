package com.bank.account.infrastructure.controller;

import com.bank.account.application.usecase.GenerateAccountMovementReportUseCase;
import com.bank.account.infrastructure.controller.dto.AccountMovementReportResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Accounts", description = "Account operations")
@RestController
@RequestMapping("/reportes")
public class AccountReportController {

    private final GenerateAccountMovementReportUseCase useCase;

    public AccountReportController(GenerateAccountMovementReportUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<List<AccountMovementReportResponseDTO>> report(
            @RequestParam String customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return ResponseEntity.ok(
                useCase.execute(customerId, from, to)
        );
    }
}