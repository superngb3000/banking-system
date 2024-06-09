package com.superngb.bankingsystem.domain.authorization;

import com.superngb.bankingsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface AuthorizationUserDataAccess {
    User findByLogin(String login);
}
