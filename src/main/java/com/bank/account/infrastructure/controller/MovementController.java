package com.bank.account.infrastructure.controller;


import com.bank.account.application.service.AccountService;
import com.bank.account.infrastructure.controller.dto.MovementRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovementController {

    private final AccountService service;

    public MovementController(AccountService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody MovementRequestDTO request) {
        service.registerMovement(request.accountNumber(), request.amount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}