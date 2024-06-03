package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import com.superngb.bankingsystem.model.user.UserUpdateModel;
import org.springframework.stereotype.Component;

@Component
public interface UserInputBoundary {

    ResponseModel<?> createUser(UserPostModel userPostModel);

    ResponseModel<?> deleteUser(Long id);

    ResponseModel<?> updateUser(UserUpdateModel userUpdateModel);
}
