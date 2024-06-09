package com.superngb.bankingsystem.domain.admin;

import com.superngb.bankingsystem.entity.Account;
import com.superngb.bankingsystem.entity.User;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserDtoModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminInteractor implements AdminInputBoundary {

    private final static Logger logger = LoggerFactory.getLogger(AdminInteractor.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AdminUserDataAccess adminUserDataAccess;
    private final AdminAccountDataAccess adminAccountDataAccess;

    public AdminInteractor(BCryptPasswordEncoder bCryptPasswordEncoder,
                           AdminUserDataAccess adminUserDataAccess,
                           AdminAccountDataAccess adminAccountDataAccess) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminUserDataAccess = adminUserDataAccess;
        this.adminAccountDataAccess = adminAccountDataAccess;
    }

    @Override
    public ResponseModel<?> createUser(UserPostModel userPostModel) {
        if (adminUserDataAccess.findByLogin(userPostModel.getLogin()) != null) {
            String message = String.format("Логин (%s) уже используется.", userPostModel.getLogin());
            logger.warn(message);
            return ResponseModel.builder().code(403).body(message).build();
        }
        if (adminUserDataAccess.findByEmail(userPostModel.getEmail()) != null) {
            String message = String.format("Email (%s) уже используется.", userPostModel.getEmail());
            logger.warn(message);
            return ResponseModel.builder().code(403).body(message).build();
        }
        if (adminUserDataAccess.findByPhone(createPhoneNumber(userPostModel.getPhone())) != null) {
            String message = String.format("Телефонный номер (%s) уже используется.", createPhoneNumber(userPostModel.getPhone()));
            logger.warn(message);
            return ResponseModel.builder().code(403).body(message).build();
        }

        List<String> phoneList = new ArrayList<>();
        phoneList.add(createPhoneNumber(userPostModel.getPhone()));
        List<String> emailList = new ArrayList<>();
        emailList.add(userPostModel.getEmail());

        User user = adminUserDataAccess.save(User.builder()
                .lastName(userPostModel.getLastName())
                .firstName(userPostModel.getFirstName())
                .patronymic(userPostModel.getPatronymic())
                .dateOfBirth(userPostModel.getDateOfBirth())
                .phoneList(phoneList)
                .emailList(emailList)
                .login(userPostModel.getLogin())
                .password(bCryptPasswordEncoder.encode(userPostModel.getPassword()))
                .account(adminAccountDataAccess.save(Account.builder()
                        .balance(userPostModel.getInitialBalance().setScale(2, RoundingMode.DOWN))
                        .initialBalance(userPostModel.getInitialBalance().setScale(2, RoundingMode.DOWN))
                        .canAccrueInterest(true)
                        .build())
                ).build());

        UserDtoModel userDtoModel = UserDtoModel.mapper(user);
        logger.info("Создан пользователь: {}.", userDtoModel);
        return ResponseModel.builder().code(201).body(userDtoModel).build();
    }

    private String createPhoneNumber(String phoneNumber) {
        return String.format("(%s) %s-%s",
                phoneNumber.substring(0, 3), phoneNumber.substring(3, 6), phoneNumber.substring(6));
    }

    @Override
    public ResponseModel<?> deleteUser(Long id) {
        User user = adminUserDataAccess.deleteById(id);
        if (user == null) {
            String message = String.format("Пользователь с id (%s) не найден.", id.toString());
            logger.warn(message);
            return ResponseModel.builder().code(404).body(message).build();
        }
        String message = String.format("Пользователь с id (%s) удален.", id.toString());
        logger.info(message);
        return ResponseModel.builder().code(200).body(message).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = adminUserDataAccess.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
