package com.superngb.bankingsystem.database;

import com.superngb.bankingsystem.domain.admin.AdminUserDataAccess;
import com.superngb.bankingsystem.entuty.User;
import com.superngb.bankingsystem.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminUserDataAccessImpl implements AdminUserDataAccess {

    private final UserRepository userRepository;

    public AdminUserDataAccessImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }
}
