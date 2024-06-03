package com.superngb.bankingsystem.repository;

import com.superngb.bankingsystem.entuty.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
