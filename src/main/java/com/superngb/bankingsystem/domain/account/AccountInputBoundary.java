package com.superngb.bankingsystem.domain.account;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface AccountInputBoundary {
    void transferMoney(String from, String to, BigDecimal amount);

    void canTransfer(BigDecimal amount);

    void accrueInterest();
}
