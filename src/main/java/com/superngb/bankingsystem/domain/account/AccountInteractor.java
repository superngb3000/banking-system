package com.superngb.bankingsystem.domain.account;

import com.superngb.bankingsystem.entity.Account;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.account.TransferRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class AccountInteractor implements AccountInputBoundary {

    private final static Logger logger = LoggerFactory.getLogger(AccountInteractor.class);

    private final AccountDataAccess accountDataAccess;

    public AccountInteractor(AccountDataAccess accountDataAccess) {
        this.accountDataAccess = accountDataAccess;
    }

    // TODO переделать параметры, сделать потокобезопасной
    @Transactional
    @Override
    public ResponseModel<?> transferMoney(TransferRequestModel transferRequestModel) {
        Long from = transferRequestModel.getFrom();
        Long to = transferRequestModel.getTo();
        BigDecimal amount = transferRequestModel.getAmount();

        if (accountDataAccess.findById(from) == null) {
            String message = String.format("Счет с id (%s) не найден.", from);
            logger.warn(message);
            return ResponseModel.builder().code(404).body(message).build();
        }
        Account accountFrom = accountDataAccess.findById(from);

        if (accountDataAccess.findById(to) == null) {
            String message = String.format("Счет с id (%s) не найден.", to);
            logger.warn(message);
            return ResponseModel.builder().code(404).body(message).build();
        }
        Account accountTo = accountDataAccess.findById(to);

        if (!canTransfer(accountFrom, amount)) {
            String message = String.format("Недостаточно средств на счету с id (%s).", from);
            logger.warn(message);
            return ResponseModel.builder().code(406).body(message).build();
        }

        BigDecimal buff = accountFrom.getBalance().subtract(amount);
        accountFrom.setBalance(buff);
        accountDataAccess.save(accountFrom);

        buff = accountTo.getBalance().add(amount);
        accountTo.setBalance(buff);
        accountDataAccess.save(accountTo);

        String message = String.format("Успешный перевод c счета с id (%s) на id (%s). На счету с id(%s) остаток: %s.", from, to, from, accountFrom.getBalance());
        logger.info(message);
        return ResponseModel.builder().code(200).body(message).build();
    }

    private boolean canTransfer(Account account, BigDecimal amount) {
        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) != -1;
    }

    @Scheduled(fixedRate = 6000)
    public void accrueInterest() {
        final BigDecimal multiplicand = BigDecimal.valueOf(1.05);
        final BigDecimal maxCoefficient = BigDecimal.valueOf(2.07);

        List<Account> accountList = accountDataAccess.getAccounts();
        for (Account account : accountList) {
            BigDecimal balance = account.getBalance();
            if (!account.isCanAccrueInterest()) {
                logger.info("Невозможно начислить проценты на счет с id ({}). Текущий баланс ({}) составляет 207% от начального депозита.",
                        account.getId(), balance);
            } else {
                BigDecimal initialBalance = account.getInitialBalance();
                if (balance.divide(initialBalance).compareTo(maxCoefficient) != -1) {
                    account.setCanAccrueInterest(false);
                    logger.info("Невозможно начислить проценты на счет с id ({}). Текущий баланс ({}) составляет 207% от начального депозита.",
                            account.getId(), balance);
                } else {
                    BigDecimal buff = balance.multiply(multiplicand);
                    if (buff.divide(initialBalance).compareTo(maxCoefficient) != -1) {
                        buff = initialBalance.multiply(maxCoefficient);
                    }
                    account.setBalance(buff);
                    accountDataAccess.save(account);
                    logger.info("Успешное начисление процентов на счет с id ({}). Текущий баланс ({}) составляет {}% от начального депозита.",
                            account.getId(), balance, balance.divide(initialBalance).multiply(BigDecimal.valueOf(100)));
                }
            }
        }
    }
}
