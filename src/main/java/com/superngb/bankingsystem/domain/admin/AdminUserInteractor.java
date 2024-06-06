package com.superngb.bankingsystem.domain.admin;

import com.superngb.bankingsystem.entuty.Account;
import com.superngb.bankingsystem.entuty.User;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserDtoModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserInteractor implements AdminUserInputBoundary {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AdminUserDataAccess adminUserDataAccess;
    private final AdminAccountDataAccess adminAccountDataAccess;

    public AdminUserInteractor(BCryptPasswordEncoder bCryptPasswordEncoder,
                               AdminUserDataAccess adminUserDataAccess,
                               AdminAccountDataAccess adminAccountDataAccess) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminUserDataAccess = adminUserDataAccess;
        this.adminAccountDataAccess = adminAccountDataAccess;
    }

    @Override
    public ResponseModel<?> createUser(UserPostModel userPostModel) {
        if (adminUserDataAccess.findByLogin(userPostModel.getLogin()) != null) {
            return ResponseModel.builder().code(403).body("Логин (" + userPostModel.getLogin() + ") уже используется").build();
        }
        if (adminUserDataAccess.findByEmail(userPostModel.getEmail()) != null) {
            return ResponseModel.builder().code(403).body("Email (" + userPostModel.getEmail() + ") уже используется").build();
        }
        if (adminUserDataAccess.findByPhone(createPhoneNumber(userPostModel.getPhone())) != null) {
            return ResponseModel.builder().code(403).body("Телефонный номер (" + userPostModel.getPhone() + ") уже используется").build();
        }

        List<String> phoneList = new ArrayList<>();
        phoneList.add(createPhoneNumber(userPostModel.getPhone()));
        List<String> emailList = new ArrayList<>();
        emailList.add(userPostModel.getEmail());

        return ResponseModel.builder().code(201).body(UserDtoModel.mapper(adminUserDataAccess.save(User.builder()
                .lastName(userPostModel.getLastName())
                .firstName(userPostModel.getFirstName())
                .patronymic(userPostModel.getPatronymic())
                .dateOfBirth(userPostModel.getDateOfBirth())
                .phone(phoneList)
                .email(emailList)
                .login(userPostModel.getLogin())
                .password(bCryptPasswordEncoder.encode(userPostModel.getPassword()))
                .account(adminAccountDataAccess.save(Account.builder()
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
        User user = adminUserDataAccess.deleteById(id);
        return (user == null)
                ? ResponseModel.builder().code(404).body("Пользователь с id (" + id.toString() + ") не найден").build()
                : ResponseModel.builder().code(200).body(UserDtoModel.mapper(user)).build();
    }
}
