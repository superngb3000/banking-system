package com.superngb.bankingsystem.domain.account;

import com.superngb.bankingsystem.entuty.Account;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.account.TransferRequestModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountInteractor implements AccountInputBoundary {

    private final AccountDataAccess accountDataAccess;

    public AccountInteractor(AccountDataAccess accountDataAccess) {
        this.accountDataAccess = accountDataAccess;
    }

    // TODO переделать параметры, сделать потокобезопасной
    @Override
    public ResponseModel<?> transferMoney(TransferRequestModel transferRequestModel) {
        Long from = transferRequestModel.getFrom();
        Long to = transferRequestModel.getTo();
        BigDecimal amount = transferRequestModel.getAmount();

        if (accountDataAccess.findById(from) == null) {
            return ResponseModel.builder().code(404).body("Счет с id (" + from + ") не найден").build();
        }
        Account accountFrom = accountDataAccess.findById(from);

        if (accountDataAccess.findById(to) == null) {
            return ResponseModel.builder().code(404).body("Счет с id (" + to + ") не найден").build();
        }
        Account accountTo = accountDataAccess.findById(to);

        if (!canTransfer(accountFrom, amount)) {
            return ResponseModel.builder().code(406).body("Недостаточно средств на счету с id (" + from + ")").build();
        }

        BigDecimal buff = accountFrom.getBalance().subtract(amount);
        accountFrom.setBalance(buff);
        accountDataAccess.save(accountFrom);

        buff = accountTo.getBalance().add(amount);
        accountTo.setBalance(buff);
        accountDataAccess.save(accountTo);

        return ResponseModel.builder().code(200).body("Успешный перевод. На счету с id(" + from + ") остаток: " + accountFrom.getBalance()).build();
    }

    private boolean canTransfer(Account account, BigDecimal amount) {
        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) != -1;
    }

    // TODO никогда не достигает 207% от изначального (баг или фича?)
    @Scheduled(fixedRate = 60000)
    public void accrueInterest() {
        List<Account> accountList = accountDataAccess.getAccounts();
        for (Account account : accountList) {
            BigDecimal buff = account.getBalance().multiply(BigDecimal.valueOf(1.05));
            if (buff.divide(account.getInitialBalance()).compareTo(BigDecimal.valueOf(2.07)) != 1) {
                account.setBalance(buff);
                accountDataAccess.save(account);
            }
        }
    }
}
