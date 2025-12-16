package com.bank.account.application.usecase;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountType;
import com.bank.account.domain.repository.AccountRepository;
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

@ContextConfiguration(classes = {UpdateAccountStatusUseCase.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UpdateAccountStatusUseCaseTest {
  @MockBean
  private AccountRepository accountRepository;

  @Autowired
  private UpdateAccountStatusUseCase updateAccountStatusUseCase;

  /**
   * Method under test:
   * {@link UpdateAccountStatusUseCase#execute(String, Boolean)}
   */
  @Test
  void testExecute() {
    // Arrange
    Optional<Account> ofResult = Optional.of(new Account("42", "42", AccountType.AHORROS, new BigDecimal("2.3"), true));
    when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    updateAccountStatusUseCase.execute("42", true);

    // Assert
    verify(accountRepository).findByAccountNumber(eq("42"));
  }
}
