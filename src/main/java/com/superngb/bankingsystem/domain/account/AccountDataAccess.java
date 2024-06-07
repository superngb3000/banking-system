package com.superngb.bankingsystem.domain.account;

import com.superngb.bankingsystem.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountDataAccess {
    Account save(Account account);

    Account findById(Long id);

    List<Account> getAccounts();
}
