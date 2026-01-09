package com.bank.account.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountType;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@DataJpaTest
class AccountRepositoryIT {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void savesAndIncrementsVersion() {
        Account account = new Account(
                "ACC-1",
                "CUST-1",
                AccountType.AHORROS,
                new BigDecimal("10.00"),
                true);

        Account saved = repository.saveAndFlush(account);
        assertThat(saved.getVersion()).isNotNull();
        Long initialVersion = saved.getVersion();

        saved.applyMovement(new BigDecimal("5.00"));
        Account updated = repository.saveAndFlush(saved);
        assertThat(updated.getVersion()).isGreaterThan(initialVersion);
    }

    @Test
    void detectsOptimisticLockConflict() {
        Account account = new Account(
                "ACC-2",
                "CUST-2",
                AccountType.CORRIENTE,
                new BigDecimal("25.00"),
                true);
        repository.saveAndFlush(account);
        entityManager.clear();

        Account first = repository.findById("ACC-2").orElseThrow();
        entityManager.detach(first);
        Account second = repository.findById("ACC-2").orElseThrow();
        entityManager.detach(second);

        first.applyMovement(new BigDecimal("3.00"));
        repository.saveAndFlush(first);

        second.applyMovement(new BigDecimal("2.00"));
        assertThrows(ObjectOptimisticLockingFailureException.class,
                () -> repository.saveAndFlush(second));
    }
}
