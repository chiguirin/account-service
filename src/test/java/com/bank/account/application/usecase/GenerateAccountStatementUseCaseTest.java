package com.bank.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.controller.dto.AccountStatementResponseDTO;
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

  /**
   * Method under test:
   * {@link GenerateAccountStatementUseCase#execute(String, LocalDateTime, LocalDateTime)}
   */
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
}
