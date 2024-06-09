package com.superngb.bankingsystem.domain.user;

import com.superngb.bankingsystem.entity.User;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserDtoModel;
import com.superngb.bankingsystem.model.user.UserUpdateEmailListModel;
import com.superngb.bankingsystem.model.user.UserUpdatePhoneListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInteractor implements UserInputBoundary {

    private final static Logger logger = LoggerFactory.getLogger(UserInteractor.class);

    private final UserUserDataAccess userUserDataAccess;

    public UserInteractor(UserUserDataAccess userUserDataAccess) {
        this.userUserDataAccess = userUserDataAccess;
    }

    @Override
    public ResponseModel<?> updateUserPhoneList(UserUpdatePhoneListModel userUpdatePhoneListModel) {
        User userById = userUserDataAccess.findById(userUpdatePhoneListModel.getId());
        if (userById == null) {
            String message = String.format("Пользователь с id (%s) не найден.", userUpdatePhoneListModel.getId().toString());
            logger.warn(message);
            return ResponseModel.builder().code(404).body(message).build();
        }
        if (userUpdatePhoneListModel.getPhoneList() != null) {
            List<String> phoneList = new ArrayList<>(userUpdatePhoneListModel.getPhoneList());
            for (String phone : phoneList) {
                if (userUserDataAccess.existsByPhoneList(createPhoneNumber(phone))) {
                    String message = String.format("Телефонный номер (%s) уже используется.", phone);
                    logger.warn(message);
                    return ResponseModel.builder().code(403).body(message).build();
                }
            }
            phoneList = phoneList.stream()
                    .distinct()
                    .map(t -> t = createPhoneNumber(t))
                    .toList();
            userById.setPhoneList(new ArrayList<>(phoneList));
        }
        userUserDataAccess.save(userById);
        logger.info("Список телефонных номеров пользователя с id={} обновлен {}.", userById.getId(), userById.getPhoneList());
        return ResponseModel.builder().code(200).body(UserDtoModel.mapper(userById)).build();
    }

    @Override
    public ResponseModel<?> updateUserEmailList(UserUpdateEmailListModel userUpdateEmailListModel) {
        User userById = userUserDataAccess.findById(userUpdateEmailListModel.getId());
        if (userById == null) {
            String message = String.format("Пользователь с id (%s) не найден.", userUpdateEmailListModel.getId().toString());
            logger.warn(message);
            return ResponseModel.builder().code(404).body(message).build();
        }
        if (userUpdateEmailListModel.getEmailList() != null) {
            List<String> emailList = new ArrayList<>(userUpdateEmailListModel.getEmailList());
            for (String email : emailList) {
                if (userUserDataAccess.existsByEmailList(email)) {
                    String message = String.format("Email (%s) уже используется.", email);
                    logger.warn(message);
                    return ResponseModel.builder().code(403).body(message).build();
                }
            }
            emailList = emailList.stream()
                    .distinct()
                    .toList();
            userById.setEmailList(new ArrayList<>(emailList));
        }
        userUserDataAccess.save(userById);
        logger.info("Список email пользователя с id={} обновлен {}.", userById.getId(), userById.getEmailList());
        return ResponseModel.builder().code(200).body(UserDtoModel.mapper(userById)).build();
    }

    private String createPhoneNumber(String phoneNumber) {
        return String.format("(%s) %s-%s",
                phoneNumber.substring(0, 3), phoneNumber.substring(3, 6), phoneNumber.substring(6));
    }
}
