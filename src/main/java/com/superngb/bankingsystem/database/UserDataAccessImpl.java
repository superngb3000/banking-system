package com.superngb.bankingsystem.database;

import com.superngb.bankingsystem.domain.admin.AdminUserDataAccess;
import com.superngb.bankingsystem.domain.search.SearchUserDataAccess;
import com.superngb.bankingsystem.domain.user.UserUserDataAccess;
import com.superngb.bankingsystem.entity.User;
import com.superngb.bankingsystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class UserDataAccessImpl implements AdminUserDataAccess, UserUserDataAccess, SearchUserDataAccess {

    private final UserRepository userRepository;

    public UserDataAccessImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByEmailList(String email) {
        return userRepository.existsByEmailList(email);
    }

    @Override
    public boolean existsByPhoneList(String phone) {
        return userRepository.existsByPhoneList(phone);
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailList(email).orElse(null);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhoneList(phone).orElse(null);
    }

    @Override
    public Page<User> filter(String phone, String email, String dateOfBirth, String fullName, Pageable pageable) {
        return userRepository.filter(phone, email, dateOfBirth, fullName, pageable);
    }
}
