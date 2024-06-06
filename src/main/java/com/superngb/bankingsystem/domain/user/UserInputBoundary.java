package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserUpdateModel;
import org.springframework.stereotype.Component;

@Component
public interface UserInputBoundary {

    ResponseModel<?> updateUser(UserUpdateModel userUpdateModel);
}
