package com.superngb.bankingsystem.domain.admin;

import com.superngb.bankingsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface AdminUserDataAccess {

    User save(User user);

    User deleteById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    User findByPhone(String phone);
}
