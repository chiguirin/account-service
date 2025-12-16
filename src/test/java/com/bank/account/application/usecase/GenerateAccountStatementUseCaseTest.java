package com.bank.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.domain.model.Movement;
import com.bank.account.domain.model.MovementType;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.controller.dto.AccountStatementResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GenerateAccountStatementUseCase.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GenerateAccountStatementUseCaseTest {
    @Autowired
    private GenerateAccountStatementUseCase generateAccountStatementUseCase;

    @MockBean
    private MovementRepository movementRepository;


    @Test
    void testExecute() {
        // Arrange
        when(movementRepository.findByAccountNumberAndDateBetween(Mockito.<String>any(), Mockito.<LocalDateTime>any(),
                Mockito.<LocalDateTime>any())).thenReturn(new ArrayList<>());
        LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

        // Act
        List<AccountStatementResponseDTO> actualExecuteResult = generateAccountStatementUseCase.execute("42", from,
                LocalDate.of(1970, 1, 1).atStartOfDay());

        // Assert
        verify(movementRepository).findByAccountNumberAndDateBetween(eq("42"), isA(LocalDateTime.class),
                isA(LocalDateTime.class));
        assertTrue(actualExecuteResult.isEmpty());
    }


    @Test
    void testExecute2() {
        // Arrange
        ArrayList<Movement> movementList = new ArrayList<>();
        BigDecimal amount = new BigDecimal("2.3");
        movementList.add(new Movement("42", MovementType.DEPOSITO, amount, new BigDecimal("2.3")));
        when(movementRepository.findByAccountNumberAndDateBetween(Mockito.<String>any(), Mockito.<LocalDateTime>any(),
                Mockito.<LocalDateTime>any())).thenReturn(movementList);
        LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

        // Act
        List<AccountStatementResponseDTO> actualExecuteResult = generateAccountStatementUseCase.execute("42", from,
                LocalDate.of(1970, 1, 1).atStartOfDay());

        // Assert
        verify(movementRepository).findByAccountNumberAndDateBetween(eq("42"), isA(LocalDateTime.class),
                isA(LocalDateTime.class));
        assertEquals(1, actualExecuteResult.size());
        AccountStatementResponseDTO getResult = actualExecuteResult.get(0);
        assertEquals("42", getResult.accountNumber());
        assertEquals("DEPOSITO", getResult.movementType());
        BigDecimal expectedAmountResult = new BigDecimal("2.3");
        assertEquals(expectedAmountResult, getResult.amount());
        BigDecimal expectedBalanceResult = new BigDecimal("2.3");
        assertEquals(expectedBalanceResult, getResult.balance());
    }


    @Test
    void testExecute3() {
        // Arrange
        ArrayList<Movement> movementList = new ArrayList<>();
        BigDecimal amount = new BigDecimal("2.3");
        movementList.add(new Movement("42", MovementType.DEPOSITO, amount, new BigDecimal("2.3")));
        BigDecimal amount2 = new BigDecimal("2.3");
        movementList.add(new Movement("42", MovementType.DEPOSITO, amount2, new BigDecimal("2.3")));
        when(movementRepository.findByAccountNumberAndDateBetween(Mockito.<String>any(), Mockito.<LocalDateTime>any(),
                Mockito.<LocalDateTime>any())).thenReturn(movementList);
        LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

        // Act
        List<AccountStatementResponseDTO> actualExecuteResult = generateAccountStatementUseCase.execute("42", from,
                LocalDate.of(1970, 1, 1).atStartOfDay());

        // Assert
        verify(movementRepository).findByAccountNumberAndDateBetween(eq("42"), isA(LocalDateTime.class),
                isA(LocalDateTime.class));
        assertEquals(2, actualExecuteResult.size());
        AccountStatementResponseDTO getResult = actualExecuteResult.get(0);
        assertEquals("42", getResult.accountNumber());
        AccountStatementResponseDTO getResult2 = actualExecuteResult.get(1);
        assertEquals("42", getResult2.accountNumber());
        assertEquals("DEPOSITO", getResult.movementType());
        assertEquals("DEPOSITO", getResult2.movementType());
        BigDecimal expectedAmountResult = new BigDecimal("2.3");
        assertEquals(expectedAmountResult, getResult.amount());
        BigDecimal expectedAmountResult2 = new BigDecimal("2.3");
        assertEquals(expectedAmountResult2, getResult2.amount());
        BigDecimal expectedBalanceResult = new BigDecimal("2.3");
        assertEquals(expectedBalanceResult, getResult.balance());
        BigDecimal expectedBalanceResult2 = new BigDecimal("2.3");
        assertEquals(expectedBalanceResult2, getResult2.balance());
    }
}
