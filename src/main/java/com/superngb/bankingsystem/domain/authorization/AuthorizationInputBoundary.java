package com.superngb.bankingsystem.domain.authorization;

import com.superngb.bankingsystem.model.LoginRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public interface AuthorizationInputBoundary extends UserDetailsService {

    ResponseModel<?> login(LoginRequestModel loginRequestModel);
}
