package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.model.user.UserPostRequestModel;
import com.superngb.bankingsystem.model.user.UserUpdateRequestModel;
import org.springframework.stereotype.Component;

@Component
public interface UserInputBoundary {
    void createUser(UserPostRequestModel userPostRequestModel);
    void updateUser(UserUpdateRequestModel userUpdateRequestModel);
}
