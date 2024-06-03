package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.entuty.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDataAccess {
    User save(User user);

    User findById(Long id);

    List<User> getUsers();

    User deleteById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    User findByPhone(String phone);
}
