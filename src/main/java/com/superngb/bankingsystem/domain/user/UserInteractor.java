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
            return ResponseModel.builder().code(403).body("Логин (" + userPostModel.getLogin() + ") уже используется").build();
        }
        if (userDataAccess.findByEmail(userPostModel.getEmail()) != null) {
            return ResponseModel.builder().code(403).body("Email (" + userPostModel.getEmail() + ") уже используется").build();
        }
        if (userDataAccess.findByPhone(userPostModel.getPhone()) != null) {
            return ResponseModel.builder().code(403).body("Телефонный номер (" + userPostModel.getPhone() + ") уже используется").build();
        }

        return ResponseModel.builder().code(201).body(UserDtoModel.mapper(userDataAccess.save(User.builder()
                .lastName(userPostModel.getLastName())
                .firstName(userPostModel.getFirstName())
                .patronymic(userPostModel.getPatronymic())
                .dateOfBirth(userPostModel.getDateOfBirth())
                .phone(createPhoneNumber(userPostModel.getPhone()))
                .email(userPostModel.getEmail())
                .login(userPostModel.getLogin())
                .password(userPostModel.getPassword())
                .account(accountDataAccess.save(Account.builder()
                        .balance(userPostModel.getInitialBalance())
                        .initialBalance(userPostModel.getInitialBalance())
                        .build())
                ).build()))).build();
    }

    private String createPhoneNumber(String phoneNumber) {
        return String.format("(%s) %s-%s",
                phoneNumber.substring(0, 3), phoneNumber.substring(3, 6), phoneNumber.substring(6));
    }

    @Override
    public ResponseModel<?> deleteUser(Long id) {
        User user = userDataAccess.deleteById(id);
        return (user == null)
                ? ResponseModel.builder().code(404).body("Пользователь с id (" + id.toString() + ") не найден").build()
                : ResponseModel.builder().code(200).body(UserDtoModel.mapper(user)).build();
    }

    @Override
    public ResponseModel<?> updateUser(UserUpdateModel userUpdateModel) {
        return null;
    }
}
