package com.superngb.bankingsystem.model.account;

import com.superngb.bankingsystem.entuty.Account;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoModel {
    private Long id;
    private BigDecimal balance;
    private BigDecimal initialBalance;

    public static AccountDtoModel mapper(Account object) {
        return new AccountDtoModel(
                object.getId(),
                object.getBalance(),
                object.getInitialBalance()
        );
    }

    public static List<AccountDtoModel> mapper(List<Account> objectList) {
        return objectList.stream()
                .map(AccountDtoModel::mapper)
                .toList();
    }
}
