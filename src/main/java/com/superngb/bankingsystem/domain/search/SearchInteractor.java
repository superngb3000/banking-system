package com.superngb.bankingsystem.domain.search;

import com.superngb.bankingsystem.entity.User;
import com.superngb.bankingsystem.model.FilterRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserDtoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchInteractor implements SearchInputBoundary {

    private final SearchUserDataAccess searchUserDataAccess;

    public SearchInteractor(SearchUserDataAccess searchUserDataAccess) {
        this.searchUserDataAccess = searchUserDataAccess;
    }

    @Override
    public ResponseModel<?> filter(FilterRequestModel filterRequestModel, Pageable pageable) {
        Page<User> userPage = searchUserDataAccess.filter(createPhoneNumber(
                filterRequestModel.getPhone()),
                filterRequestModel.getEmail(),
                filterRequestModel.getDateOfBirth(),
                filterRequestModel.getFullName(),
                pageable);
        Page<UserDtoModel> userDtoModelPage = userPage.map(UserDtoModel::mapper);
        return ResponseModel.builder().code(200).body(userDtoModelPage).build();
    }

    private String createPhoneNumber(String phoneNumber) {
        return (phoneNumber == null)
                ? null
                : String.format("(%s) %s-%s", phoneNumber.substring(0, 3), phoneNumber.substring(3, 6), phoneNumber.substring(6));
    }
}
