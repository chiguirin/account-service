package com.bank.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountType;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.exception.BusinessException;
import com.bank.account.infrastructure.controller.dto.AccountResponseDTO;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetAccountByNumberUseCase.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GetAccountByNumberUseCaseTest {
  @MockBean
  private AccountRepository accountRepository;

  @Autowired
  private GetAccountByNumberUseCase getAccountByNumberUseCase;

  /**
   * Method under test: {@link GetAccountByNumberUseCase#execute(String)}
   */
  @Test
  void testExecute() {
    // Arrange
    Optional<Account> ofResult = Optional.of(new Account("42", "42", AccountType.AHORROS, new BigDecimal("2.3"), true));
    when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    AccountResponseDTO actualExecuteResult = getAccountByNumberUseCase.execute("42");

    // Assert
    verify(accountRepository).findByAccountNumber(eq("42"));
    assertEquals("42", actualExecuteResult.accountNumber());
    assertEquals("42", actualExecuteResult.customerId());
    assertEquals("AHORROS", actualExecuteResult.accountType());
    assertTrue(actualExecuteResult.active());
    BigDecimal expectedBalanceResult = new BigDecimal("2.3");
    assertEquals(expectedBalanceResult, actualExecuteResult.balance());
  }

  /**
   * Method under test: {@link GetAccountByNumberUseCase#execute(String)}
   */
  @Test
  void testExecute2() {
    // Arrange
    Account account = mock(Account.class);
    when(account.getCurrentBalance()).thenThrow(new BusinessException("An error occurred"));
    when(account.getAccountType()).thenReturn(AccountType.AHORROS);
    when(account.getAccountNumber()).thenReturn("42");
    when(account.getCustomerId()).thenReturn("42");
    Optional<Account> ofResult = Optional.of(account);
    when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(BusinessException.class, () -> getAccountByNumberUseCase.execute("42"));
    verify(account).getAccountNumber();
    verify(account).getAccountType();
    verify(account).getCurrentBalance();
    verify(account).getCustomerId();
    verify(accountRepository).findByAccountNumber(eq("42"));
  }

  /**
   * Method under test: {@link GetAccountByNumberUseCase#execute(String)}
   */
  @Test
  void testExecute3() {
    // Arrange
    Optional<Account> emptyResult = Optional.empty();
    when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(emptyResult);
    new BusinessException("An error occurred");
    new BusinessException("An error occurred");

    // Act and Assert
    assertThrows(BusinessException.class, () -> getAccountByNumberUseCase.execute("42"));
    verify(accountRepository).findByAccountNumber(eq("42"));
  }
}
