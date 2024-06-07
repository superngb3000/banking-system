package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserUserDataAccess {

    User save(User user);

    User findById(Long id);

    boolean existsByEmailList(String email);

    boolean existsByPhoneList(String phone);
}
