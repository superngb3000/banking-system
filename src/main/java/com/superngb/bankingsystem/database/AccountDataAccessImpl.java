package com.superngb.bankingsystem.database;

import com.superngb.bankingsystem.domain.account.AccountDataAccess;
import com.superngb.bankingsystem.domain.admin.AdminAccountDataAccess;
import com.superngb.bankingsystem.entity.Account;
import com.superngb.bankingsystem.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDataAccessImpl implements AccountDataAccess, AdminAccountDataAccess {

    private final AccountRepository accountRepository;

    public AccountDataAccessImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
