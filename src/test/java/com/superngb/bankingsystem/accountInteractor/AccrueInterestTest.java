package com.superngb.bankingsystem.accountInteractor;

import com.superngb.bankingsystem.domain.account.AccountDataAccess;
import com.superngb.bankingsystem.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccrueInterestTest {

    @Autowired
    private AccountDataAccess accountDataAccess;

    @Test
    void testCanAccrueInterest() throws InterruptedException {
        Account account1 = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN))
                .initialBalance(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN))
                .canAccrueInterest(true)
                .build();
        accountDataAccess.save(account1);

        Thread.sleep(130000);
        assertThat(accountDataAccess.findById(1L).getBalance().setScale(2, RoundingMode.DOWN)).isGreaterThan(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN));
    }

    @Test
    void testCanNotAccrueInterest() throws InterruptedException {
        Account account1 = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(207).setScale(2, RoundingMode.DOWN))
                .initialBalance(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN))
                .canAccrueInterest(false)
                .build();
        accountDataAccess.save(account1);

        Thread.sleep(130000);
        assertThat(accountDataAccess.findById(1L).getBalance().setScale(2, RoundingMode.DOWN)).isEqualTo(BigDecimal.valueOf(207).setScale(2, RoundingMode.DOWN));
    }

    @Test
    void testReachedLimit() throws InterruptedException {
        Account account1 = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(206).setScale(2, RoundingMode.DOWN))
                .initialBalance(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN))
                .canAccrueInterest(true)
                .build();
        accountDataAccess.save(account1);

        Thread.sleep(130000);
        assertThat(accountDataAccess.findById(1L).getBalance().setScale(2, RoundingMode.DOWN)).isEqualTo(BigDecimal.valueOf(207).setScale(2, RoundingMode.DOWN));
    }
}
