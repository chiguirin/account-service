package com.bank.account.domain.repository;

import com.bank.account.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountNumberAndDateBetween(
            String accountNumber,
            LocalDateTime from,
            LocalDateTime to
    );

}