package com.superngb.bankingsystem.domain.admin;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import org.springframework.stereotype.Component;

@Component
public interface AdminUserInputBoundary {

    ResponseModel<?> createUser(UserPostModel userPostModel);

    ResponseModel<?> deleteUser(Long id);
}
