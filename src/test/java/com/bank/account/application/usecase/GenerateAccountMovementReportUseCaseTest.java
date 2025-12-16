package com.bank.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountType;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.client.CustomerClient;
import com.bank.account.infrastructure.client.dto.CustomerStatusDTO;
import com.bank.account.infrastructure.controller.dto.AccountMovementReportResponseDTO;
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

@ContextConfiguration(classes = {GenerateAccountMovementReportUseCase.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GenerateAccountMovementReportUseCaseTest {
  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private CustomerClient customerClient;

  @Autowired
  private GenerateAccountMovementReportUseCase generateAccountMovementReportUseCase;

  @MockBean
  private MovementRepository movementRepository;

  /**
   * Method under test:
   * {@link GenerateAccountMovementReportUseCase#execute(String, LocalDateTime, LocalDateTime)}
   */
  @Test
  void testExecute() {
    // Arrange
    when(accountRepository.findByCustomerId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(customerClient.getCustomerStatus(Mockito.<String>any())).thenReturn(new CustomerStatusDTO("42", true));
    LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<AccountMovementReportResponseDTO> actualExecuteResult = generateAccountMovementReportUseCase.execute("42",
        from, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(accountRepository).findByCustomerId(eq("42"));
    verify(customerClient).getCustomerStatus(eq("42"));
    assertTrue(actualExecuteResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link GenerateAccountMovementReportUseCase#execute(String, LocalDateTime, LocalDateTime)}
   */
  @Test
  void testExecute2() {
    // Arrange
    ArrayList<Account> accountList = new ArrayList<>();
    accountList.add(new Account("42", "42", AccountType.AHORROS, new BigDecimal("2.3"), true));
    when(accountRepository.findByCustomerId(Mockito.<String>any())).thenReturn(accountList);
    when(movementRepository.findByAccountNumberAndDateBetween(Mockito.<String>any(), Mockito.<LocalDateTime>any(),
        Mockito.<LocalDateTime>any())).thenReturn(new ArrayList<>());
    when(customerClient.getCustomerStatus(Mockito.<String>any())).thenReturn(new CustomerStatusDTO("42", true));
    LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<AccountMovementReportResponseDTO> actualExecuteResult = generateAccountMovementReportUseCase.execute("42",
        from, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(accountRepository).findByCustomerId(eq("42"));
    verify(movementRepository).findByAccountNumberAndDateBetween(eq("42"), isA(LocalDateTime.class),
        isA(LocalDateTime.class));
    verify(customerClient).getCustomerStatus(eq("42"));
    assertTrue(actualExecuteResult.isEmpty());
  }
}
