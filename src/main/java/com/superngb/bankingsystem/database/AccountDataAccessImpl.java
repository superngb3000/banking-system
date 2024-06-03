package com.superngb.bankingsystem.database;

import com.superngb.bankingsystem.domain.account.AccountDataAccess;
import com.superngb.bankingsystem.entuty.Account;
import com.superngb.bankingsystem.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountDataAccessImpl implements AccountDataAccess {

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

    @Override
    public Account deleteById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return optionalAccount.get();
        }
        return null;
    }
}
