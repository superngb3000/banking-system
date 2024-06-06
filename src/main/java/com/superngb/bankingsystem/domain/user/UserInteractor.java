package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserUpdateModel;
import org.springframework.stereotype.Service;

@Service
public class UserInteractor implements UserInputBoundary {

    private final UserUserDataAccess userDataAccess;

    public UserInteractor(UserUserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    @Override
    public ResponseModel<?> updateUser(UserUpdateModel userUpdateModel) {
        return null;
    }
}
