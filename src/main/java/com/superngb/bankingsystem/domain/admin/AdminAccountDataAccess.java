package com.superngb.bankingsystem.domain.admin;

import com.superngb.bankingsystem.entuty.Account;
import org.springframework.stereotype.Component;

@Component
public interface AdminAccountDataAccess {

    Account save(Account account);
}
