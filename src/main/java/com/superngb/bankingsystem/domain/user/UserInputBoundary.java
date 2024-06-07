package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserUpdateEmailListModel;
import com.superngb.bankingsystem.model.user.UserUpdatePhoneListModel;
import org.springframework.stereotype.Component;

@Component
public interface UserInputBoundary {

    ResponseModel<?> updateUserPhoneList(UserUpdatePhoneListModel userUpdatePhoneListModel);

    ResponseModel<?> updateUserEmailList(UserUpdateEmailListModel userUpdateEmailListModel);
}
