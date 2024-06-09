package com.superngb.bankingsystem.accountInteractor;

import com.superngb.bankingsystem.domain.account.AccountDataAccess;
import com.superngb.bankingsystem.domain.account.AccountInteractor;
import com.superngb.bankingsystem.entity.Account;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.account.TransferRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TransferMoneyTest {

    @Mock
    private AccountDataAccess accountDataAccess;

    @InjectMocks
    private AccountInteractor accountInteractor;

    @BeforeEach
    public void setup() {
        Account account1 = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .initialBalance(BigDecimal.valueOf(100))
                .canAccrueInterest(true)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .balance(BigDecimal.valueOf(100))
                .initialBalance(BigDecimal.valueOf(100))
                .canAccrueInterest(true)
                .build();

        when(accountDataAccess.findById(1L)).thenReturn(account1);
        when(accountDataAccess.findById(2L)).thenReturn(account2);
        when(accountDataAccess.findById(4L)).thenReturn(null);
    }

    @Test
    void testAccountFromIsAccountTo() {
        TransferRequestModel transferRequestModel = new TransferRequestModel(1L, 1L, BigDecimal.valueOf(100));

        ResponseModel<?> responseModel = accountInteractor.transferMoney(transferRequestModel);

        Assertions.assertEquals((Integer) 403, responseModel.getCode());
        Assertions.assertEquals("Невозможно совершить перевод средств со счета с id (1) счет с id(1). Счет списания совпадает со счетом начисления.", responseModel.getBody());
    }

    @Test
    void testAccountFromNotFound() {
        TransferRequestModel transferRequestModel = new TransferRequestModel(4L, 2L, BigDecimal.valueOf(100));

        ResponseModel<?> responseModel = accountInteractor.transferMoney(transferRequestModel);

        Assertions.assertEquals((Integer) 404, responseModel.getCode());
        Assertions.assertEquals("Счет с id (4) не найден.", responseModel.getBody());
    }

    @Test
    void testAccountToNotFound() {
        TransferRequestModel transferRequestModel = new TransferRequestModel(1L, 4L, BigDecimal.valueOf(100));

        ResponseModel<?> responseModel = accountInteractor.transferMoney(transferRequestModel);

        Assertions.assertEquals((Integer) 404, responseModel.getCode());
        Assertions.assertEquals("Счет с id (4) не найден.", responseModel.getBody());
    }

    @Test
    void testNotEnoughMoney() {
        TransferRequestModel transferRequestModel = new TransferRequestModel(1L, 2L, BigDecimal.valueOf(1000));

        ResponseModel<?> responseModel = accountInteractor.transferMoney(transferRequestModel);

        Assertions.assertEquals((Integer) 406, responseModel.getCode());
        Assertions.assertEquals("Недостаточно средств на счету с id (1).", responseModel.getBody());
    }

    @Test
    void testSuccess() {
        TransferRequestModel transferRequestModel = new TransferRequestModel(1L, 2L, BigDecimal.valueOf(100));

        ResponseModel<?> responseModel = accountInteractor.transferMoney(transferRequestModel);

        Assertions.assertEquals((Integer) 200, responseModel.getCode());
        Assertions.assertEquals("Успешный перевод c счета с id (1) на id (2). На счету с id(1) остаток: 0.00.", responseModel.getBody());
    }
}
