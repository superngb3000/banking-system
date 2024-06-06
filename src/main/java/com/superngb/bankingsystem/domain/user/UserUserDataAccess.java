package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.entuty.User;
import org.springframework.stereotype.Component;

@Component
public interface UserUserDataAccess {

    User save(User user);

    User findById(Long id);

    User deleteById(Long id);

    User findByEmail(String email);

    User findByPhone(String phone);
}
