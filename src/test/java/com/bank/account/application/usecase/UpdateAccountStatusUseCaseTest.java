package com.bank.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountType;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.exception.BusinessException;

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


    @Test
    void testExecute2() {
        // Arrange
        Account account = mock(Account.class);
        doThrow(new BusinessException("An error occurred")).when(account).setActive(Mockito.<Boolean>any());
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(BusinessException.class, () -> updateAccountStatusUseCase.execute("42", true));
        verify(account).setActive(eq(true));
        verify(accountRepository).findByAccountNumber(eq("42"));
    }


    @Test
    void testExecute3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findByAccountNumber(Mockito.<String>any())).thenReturn(emptyResult);
        new BusinessException("An error occurred");

        // Act and Assert
        assertThrows(BusinessException.class, () -> updateAccountStatusUseCase.execute("42", true));
        verify(accountRepository).findByAccountNumber(eq("42"));
    }
}
