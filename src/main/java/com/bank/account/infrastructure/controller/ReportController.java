package com.bank.account.infrastructure.controller;


import com.bank.account.application.usecase.GenerateAccountStatementUseCase;
import com.bank.account.infrastructure.controller.dto.AccountStatementResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Accounts", description = "Account operations")
@RestController
@RequestMapping("/reporte")
public class ReportController {

    private final GenerateAccountStatementUseCase useCase;

    public ReportController(GenerateAccountStatementUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<AccountStatementResponseDTO> generate(
            @RequestParam String accountNumber,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        return useCase.execute(accountNumber, from, to);
    }
}