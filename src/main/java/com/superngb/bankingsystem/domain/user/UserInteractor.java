package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.domain.account.AccountDataAccess;
import com.superngb.bankingsystem.entuty.Account;
import com.superngb.bankingsystem.entuty.User;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserDtoModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import com.superngb.bankingsystem.model.user.UserUpdateModel;
import org.springframework.stereotype.Service;

@Service
public class UserInteractor implements UserInputBoundary {

    private final UserDataAccess userDataAccess;
    private final AccountDataAccess accountDataAccess;

    public UserInteractor(UserDataAccess userDataAccess,
                          AccountDataAccess accountDataAccess) {
        this.userDataAccess = userDataAccess;
        this.accountDataAccess = accountDataAccess;
    }

    @Override
    public ResponseModel<?> createUser(UserPostModel userPostModel) {
        if (userDataAccess.findByLogin(userPostModel.getLogin()) != null) {
            return ResponseModel.builder().code(403).body("This login (" + userPostModel.getLogin() + ") has already been registered").build();
        }
        if (userDataAccess.findByEmail(userPostModel.getEmail()) != null) {
            return ResponseModel.builder().code(403).body("This email (" + userPostModel.getEmail() + ") has already been registered").build();
        }
        if (userDataAccess.findByPhone(userPostModel.getPhone()) != null) {
            return ResponseModel.builder().code(403).body("This phone (" + userPostModel.getPhone() + ") has already been registered").build();
        }

        return ResponseModel.builder().code(201).body(UserDtoModel.mapper(userDataAccess.save(User.builder()
                .lastName(userPostModel.getLastName())
                .firstName(userPostModel.getFirstName())
                .patronymic(userPostModel.getPatronymic())
                .dateOfBirth(userPostModel.getDateOfBirth())
                .phone(userPostModel.getPhone())
                .email(userPostModel.getEmail())
                .login(userPostModel.getLogin())
                .password(userPostModel.getPassword())
                .account(accountDataAccess.save(Account.builder()
                        .balance(userPostModel.getInitialBalance())
                        .initialBalance(userPostModel.getInitialBalance())
                        .build())
                ).build()))).build();
    }

    @Override
    public ResponseModel<?> deleteUser(Long id) {
        User user = userDataAccess.deleteById(id);
        return (user == null)
                ? ResponseModel.builder().code(404).body("User with userId = " + id.toString() + " not found").build()
                : ResponseModel.builder().code(200).body(UserDtoModel.mapper(user)).build();
    }

    @Override
    public ResponseModel<?> updateUser(UserUpdateModel userUpdateModel) {
        return null;
    }
}
